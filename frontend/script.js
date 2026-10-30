const API_BASE = "http://localhost:8081";
const state = { books: [], students: [], issues: [], active: [], overdue: [], historyMode: "all" };
const $ = (id) => document.getElementById(id);

async function api(path, options = {}) {
  const config = { ...options, headers: { ...(options.body ? { "Content-Type": "application/json" } : {}), ...(options.headers || {}) } };
  let response;
  try { response = await fetch(`${API_BASE}${path}`, config); }
  catch (err) { setApiStatus(false); throw new Error("Cannot connect to the backend. Check that Spring Boot is running on port 8081 and CORS is configured."); }
  if (response.ok) {
    setApiStatus(true);
    if (response.status === 204) return null;
    const text = await response.text();
    return text ? JSON.parse(text) : null;
  }
  let message = `Request failed (${response.status})`;
  try {
    const data = await response.json();
    message = data.message || data.error || message;
  } catch (_) {
    const text = await response.text().catch(() => "");
    if (text) message = text;
  }
  throw new Error(message);
}
function setApiStatus(online) {
  const el = $("apiStatus");
  el.textContent = online ? "Backend connected" : "Backend unavailable";
  el.parentElement.classList.toggle("online", online);
  el.parentElement.classList.toggle("offline", !online);
}
function toast(message, type = "success") {
  const el = document.createElement("div");
  el.className = `toast ${type}`; el.textContent = message;
  $("toastRegion").appendChild(el);
  setTimeout(() => el.remove(), 3800);
}
function esc(value) { return String(value ?? "").replace(/[&<>"']/g, c => ({ "&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;" }[c])); }
function date(value) { return value ? new Date(`${value}T00:00:00`).toLocaleDateString("en-IN",{day:"2-digit",month:"short",year:"numeric"}) : "—"; }
function money(value) { return value == null ? "—" : `₹${Number(value).toFixed(2)}`; }
function issueStatus(issue) {
  if (issue.returnDate) return ["Returned","returned"];
  if (issue.dueDate && new Date(`${issue.dueDate}T00:00:00`) < new Date(new Date().toDateString())) return ["Overdue","overdue"];
  return ["Issued","issued"];
}
function bookName(issue) { return issue.book?.title || `Book #${issue.book?.id ?? "?"}`; }
function studentName(issue) { return issue.student?.name || `Student #${issue.student?.id ?? "?"}`; }
function emptyRow(cols, message = "No records found.") { return `<tr><td colspan="${cols}" class="empty-cell">${esc(message)}</td></tr>`; }

function navigate(page) {
  document.querySelectorAll(".page-section").forEach(el => el.classList.toggle("active", el.id === `page-${page}`));
  document.querySelectorAll(".nav-link").forEach(el => el.classList.toggle("active", el.dataset.page === page));
  const names = {dashboard:"Dashboard",books:"Books",students:"Students",issue:"Issue / Return",history:"Issue History"};
  $("pageCrumb").textContent = names[page] || "Dashboard";
  $("sidebar").classList.remove("open");
  if (page === "books") loadBooks();
  if (page === "students") loadStudents();
  if (page === "issue") loadIssueData();
  if (page === "history") loadHistory();
}
document.querySelectorAll("[data-page]").forEach(btn => btn.addEventListener("click", () => navigate(btn.dataset.page)));
document.querySelectorAll("[data-goto]").forEach(btn => btn.addEventListener("click", () => navigate(btn.dataset.goto)));
$("mobileMenu").addEventListener("click", () => $("sidebar").classList.toggle("open"));
document.querySelectorAll("[data-close]").forEach(btn => btn.addEventListener("click", () => $(btn.dataset.close).close()));
$("year").textContent = new Date().getFullYear();

async function loadBooks() {
  try {
    state.books = await api("/books") || [];
    renderBooks(); updateStats();
  } catch (e) { toast(e.message,"error"); $("booksTable").innerHTML = emptyRow(6,"Could not load books."); }
}
function renderBooks() {
  const q = $("bookSearch").value.trim().toLowerCase();
  const filter = $("bookFilter").value;
  const list = state.books.filter(b => {
    const matches = [b.title,b.author,b.category,b.isbn].some(v => String(v || "").toLowerCase().includes(q));
    return matches && (filter !== "available" || b.available === true);
  });
  $("bookCount").textContent = `${list.length} book${list.length === 1 ? "" : "s"} shown`;
  $("booksTable").innerHTML = list.length ? list.map(b => `<tr>
    <td><div class="book-cell"><span class="mini-cover">▤</span><span><strong>${esc(b.title)}</strong><small>ID: ${esc(b.id)}</small></span></div></td>
    <td>${esc(b.author)}</td><td>${esc(b.isbn)}</td><td>${esc(b.category)}</td>
    <td><span class="status ${b.available ? "available" : "issued"}">${b.available ? "Available" : "Issued"}</span></td>
    <td><div class="action-group"><button class="icon-btn" data-edit-book="${b.id}">Edit</button><button class="icon-btn delete" data-delete-book="${b.id}">Delete</button></div></td>
  </tr>`).join("") : emptyRow(6);
  document.querySelectorAll("[data-edit-book]").forEach(btn => btn.addEventListener("click", () => openBookModal(Number(btn.dataset.editBook))));
  document.querySelectorAll("[data-delete-book]").forEach(btn => btn.addEventListener("click", () => deleteBook(Number(btn.dataset.deleteBook))));
}
$("bookSearch").addEventListener("input", renderBooks);
$("bookFilter").addEventListener("change", renderBooks);
$("refreshBooks").addEventListener("click", loadBooks);
$("addBookBtn").addEventListener("click", () => openBookModal());
function openBookModal(id) {
  const b = state.books.find(x => x.id === id);
  $("bookForm").reset(); $("bookId").value = b?.id || "";
  $("bookModalTitle").textContent = b ? "Edit book" : "Add new book";
  $("bookTitle").value = b?.title || ""; $("bookAuthor").value = b?.author || "";
  $("bookIsbn").value = b?.isbn || ""; $("bookCategory").value = b?.category || "";
  $("bookAvailable").checked = b ? !!b.available : true;
  $("bookModal").showModal();
}
$("bookForm").addEventListener("submit", async e => {
  e.preventDefault();
  const id = $("bookId").value;
  const payload = { title:$("bookTitle").value.trim(), author:$("bookAuthor").value.trim(), isbn:$("bookIsbn").value.trim(), category:$("bookCategory").value.trim(), available:$("bookAvailable").checked };
  try {
    await api(id ? `/books/${id}` : "/books", { method:id ? "PUT" : "POST", body:JSON.stringify(payload) });
    $("bookModal").close(); toast(id ? "Book updated successfully." : "Book added successfully.");
    await loadBooks(); await loadIssueData();
  } catch (e) { toast(e.message,"error"); }
});
async function deleteBook(id) {
  if (!confirm("Delete this book? This may fail if the book is linked to issue records.")) return;
  try { await api(`/books/${id}`,{method:"DELETE"}); toast("Book deleted."); await loadBooks(); }
  catch(e){toast(e.message,"error");}
}

async function loadStudents() {
  try { state.students = await api("/students") || []; renderStudents(); updateStats(); }
  catch(e){toast(e.message,"error");$("studentsTable").innerHTML=emptyRow(5,"Could not load students.");}
}
function renderStudents() {
  const q = $("studentSearch").value.trim().toLowerCase();
  const list = state.students.filter(s => [s.name,s.email,s.phone,s.address].some(v => String(v||"").toLowerCase().includes(q)));
  $("studentCount").textContent = `${list.length} student${list.length===1?"":"s"} shown`;
  $("studentsTable").innerHTML = list.length ? list.map(s => `<tr>
    <td><div class="student-cell"><span class="avatar">${esc((s.name||"S").charAt(0).toUpperCase())}</span><span><strong>${esc(s.name)}</strong><small>ID: ${esc(s.id)}</small></span></div></td>
    <td>${esc(s.email)}</td><td>${esc(s.phone)}</td><td>${esc(s.address)}</td>
    <td><div class="action-group"><button class="icon-btn" data-edit-student="${s.id}">Edit</button><button class="icon-btn delete" data-delete-student="${s.id}">Delete</button></div></td>
  </tr>`).join("") : emptyRow(5);
  document.querySelectorAll("[data-edit-student]").forEach(btn=>btn.addEventListener("click",()=>openStudentModal(Number(btn.dataset.editStudent))));
  document.querySelectorAll("[data-delete-student]").forEach(btn=>btn.addEventListener("click",()=>deleteStudent(Number(btn.dataset.deleteStudent))));
}
$("studentSearch").addEventListener("input",renderStudents);
$("refreshStudents").addEventListener("click",loadStudents);
$("addStudentBtn").addEventListener("click",()=>openStudentModal());
function openStudentModal(id) {
  const s=state.students.find(x=>x.id===id);
  $("studentForm").reset();$("studentId").value=s?.id||"";
  $("studentModalTitle").textContent=s?"Edit student":"Register student";
  $("studentName").value=s?.name||"";$("studentEmail").value=s?.email||"";
  $("studentPhone").value=s?.phone||"";$("studentAddress").value=s?.address||"";
  $("studentModal").showModal();
}
$("studentForm").addEventListener("submit",async e=>{
  e.preventDefault();const id=$("studentId").value;
  const payload={name:$("studentName").value.trim(),email:$("studentEmail").value.trim(),phone:$("studentPhone").value.trim(),address:$("studentAddress").value.trim()};
  try{await api(id?`/students/${id}`:"/students",{method:id?"PUT":"POST",body:JSON.stringify(payload)});$("studentModal").close();toast(id?"Student updated.":"Student registered.");await loadStudents();await loadIssueData();}
  catch(e){toast(e.message,"error");}
});
async function deleteStudent(id){
  if(!confirm("Delete this student? This may fail if the student has issue history."))return;
  try{await api(`/students/${id}`,{method:"DELETE"});toast("Student deleted.");await loadStudents();}
  catch(e){toast(e.message,"error");}
}

async function loadIssueData() {
  try {
    const [students,books,active]=await Promise.all([api("/students"),api("/books/available"),api("/issues/active")]);
    state.students=students||[];state.books=await api("/books")||[];state.active=active||[];
    $("issueStudent").innerHTML='<option value="">Select student</option>'+state.students.map(s=>`<option value="${s.id}">${esc(s.name)} (ID: ${s.id})</option>`).join("");
    $("issueBook").innerHTML='<option value="">Select available book</option>'+(books||[]).map(b=>`<option value="${b.id}">${esc(b.title)} — ${esc(b.author)} (ID: ${b.id})</option>`).join("");
    $("returnIssue").innerHTML='<option value="">Select active issue</option>'+state.active.map(i=>`<option value="${i.id}">#${i.id} — ${esc(bookName(i))} / ${esc(studentName(i))}</option>`).join("");
    renderActive();updateStats();
  }catch(e){toast(e.message,"error");}
}
$("issueForm").addEventListener("submit",async e=>{
  e.preventDefault();const studentId=Number($("issueStudent").value),bookId=Number($("issueBook").value);
  try{await api("/issues",{method:"POST",body:JSON.stringify({student:{id:studentId},book:{id:bookId,available:true}})});toast("Book issued successfully.");$("issueForm").reset();await loadIssueData();await loadHistory();}
  catch(e){toast(e.message,"error");}
});
$("returnForm").addEventListener("submit",async e=>{
  e.preventDefault();const id=$("returnIssue").value;
  try{const result=await api(`/issues/${id}/return`,{method:"PUT"});$("returnForm").reset();toast(`Book returned. Fine: ${money(result?.fine ?? 0)}`);await loadIssueData();await loadHistory();}
  catch(e){toast(e.message,"error");}
});
$("refreshActive").addEventListener("click",loadIssueData);
function renderActive(){
  $("activeTable").innerHTML=state.active.length?state.active.map(i=>`<tr><td><strong>${esc(bookName(i))}</strong></td><td>${esc(studentName(i))}</td><td>${date(i.issueDate)}</td><td>${date(i.dueDate)}</td><td><span class="status ${issueStatus(i)[1]}">${issueStatus(i)[0]}</span></td></tr>`).join(""):emptyRow(5,"No active issues.");
}

async function loadHistory(mode=state.historyMode, lookupId=null){
  state.historyMode=mode;
  try{
    let path="/issues";
    if(mode==="active")path="/issues/active";
    if(mode==="overdue")path="/issues/overdue";
    if(mode==="student"&&lookupId)path=`/issues/student/${lookupId}`;
    if(mode==="book"&&lookupId)path=`/issues/book/${lookupId}`;
    const data=await api(path);state.issues=data||[];
    const titles={all:"All issue records",active:"Currently issued books",overdue:"Overdue books",student:`Issue history — Student #${lookupId}`,book:`Issue history — Book #${lookupId}`};
    $("historyTitle").textContent=titles[mode]||titles.all;
    $("historyCount").textContent=`${state.issues.length} record${state.issues.length===1?"":"s"}`;
    $("historyTable").innerHTML=state.issues.length?state.issues.map(i=>{
      const [label,cls]=issueStatus(i);
      return `<tr><td><strong>${esc(bookName(i))}</strong><small class="muted-cell">ID: ${esc(i.book?.id)}</small></td><td>${esc(studentName(i))}</td><td>${date(i.issueDate)}</td><td>${date(i.dueDate)}</td><td>${date(i.returnDate)}</td><td>${money(i.fine)}</td><td><span class="status ${cls}">${label}</span></td></tr>`;
    }).join(""):emptyRow(7);
  }catch(e){toast(e.message,"error");$("historyTable").innerHTML=emptyRow(7,"Could not load issue records.");}
}
document.querySelectorAll("[data-history]").forEach(btn=>btn.addEventListener("click",()=>{
  document.querySelectorAll("[data-history]").forEach(b=>b.classList.toggle("active",b===btn));
  const mode=btn.dataset.history;$("historyLookup").hidden=!(mode==="student"||mode==="book");
  if(mode==="student"||mode==="book"){$("historyTitle").textContent=mode==="student"?"Search by student ID":"Search by book ID";$("historyCount").textContent="Enter an ID to view matching records.";$("historyTable").innerHTML=emptyRow(7,"Enter an ID above and select Search.");}
  else loadHistory(mode);
}));
$("historySearchBtn").addEventListener("click",()=>{
  const id=Number($("historyId").value);
  if(!id){toast("Please enter a valid ID.","error");return;}
  loadHistory(state.historyMode,id);
});
$("refreshHistory").addEventListener("click",()=>loadHistory());
async function updateStats(){
  try{
    const [books,students,active,overdue]=await Promise.all([api("/books"),api("/students"),api("/issues/active"),api("/issues/overdue")]);
    $("statBooks").textContent=(books||[]).length;$("statStudents").textContent=(students||[]).length;
    $("statActive").textContent=(active||[]).length;$("statOverdue").textContent=(overdue||[]).length;
    const recent=[...(await api("/issues")||[])].sort((a,b)=>String(b.issueDate||"").localeCompare(String(a.issueDate||""))).slice(0,5);
    $("recentIssues").innerHTML=recent.length?recent.map(i=>{const [label,cls]=issueStatus(i);return `<tr><td><div class="book-cell"><span class="mini-cover">▤</span><span><strong>${esc(bookName(i))}</strong><small>Issue #${esc(i.id)}</small></span></div></td><td>${esc(studentName(i))}</td><td>${date(i.issueDate)}</td><td><span class="status ${cls}">${label}</span></td></tr>`;}).join(""):emptyRow(4,"No issue activity yet.");
  }catch(e){setApiStatus(false);}
}
async function init(){
  try{await Promise.all([loadBooks(),loadStudents(),loadIssueData(),loadHistory("all")]);await updateStats();}
  catch(e){toast(e.message,"error");}
}
init();
