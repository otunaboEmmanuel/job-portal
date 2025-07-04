﻿# job-portal
  🎓 Internship & Job Portal for University Students

A full-featured *Job & Internship Application Portal* built with *Spring Boot, **PostgreSQL, and **OpenAI Resume Ranking* — designed to help students apply to opportunities and employers find top talent through intelligent filtering.


---

## 🔍 Features

### 🧑‍🎓 Student
- Register/Login via Google or Email (OAuth2 + JWT)
- View available internships and jobs
- Upload resumes and apply to listings
- Receive application status via email
- ### 🧑‍💼 Employer
- Post job/internship opportunities
- View applicants for each listing
- Automatically rank candidates using AI based on resume and JD match

### 🛡 Admin
- Approve or reject job listings
- Manage users and monitor system activity

---

## 🧠 AI Resume Ranking (OpenAI)

> Integrated using Spring AI and OpenAI GPT-4

- Extracts *resume content* from uploaded files (PDF/DOCX)
- Matches resume against job description
- Ranks each applicant with a *0–100 match score*
- Employers can *sort by AI score* to find top candidates


