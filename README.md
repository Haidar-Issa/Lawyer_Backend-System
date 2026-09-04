# ⚖️ Lawyer System - Backend Services

A comprehensive backend system built with **Spring Boot 3** and **Java 21** designed to manage law firm operations, case tracking, document control, and party management.

---

## 🚀 Key Features

* **Document Management & Storage**:
  * AWS S3 integration using **Presigned URLs** for direct, high-performance client-side uploads.
  * Extensible configuration compatible with **MinIO** or S3-compatible cloud providers.
* **Case & Party Management**:
  * Tracking opposing parties, court details, and case files.
  * Dynamic query filtering via Spring Data JPA.
* **Security & Authorization**:
  * Role-based access control (RBAC) via Spring Security and JWT.
* **API Architecture**:
  * Standardized response structure (`ApiResponse<T>`).
  * Full pagination and dynamic sorting support.

---

## 🛠️ Tech Stack

* **Language**: Java 21
* **Framework**: Spring Boot 3.x
* **Database**: PostgreSQL / Spring Data JPA
* **Cloud Storage**: AWS SDK v2 (S3 & Presigner)
* **Build Tool**: Maven
* **Documentation/Testing**: Swagger UI / OpenAPI

---

## ⚙️ Environment Configuration

Add the following properties to your `application.yml` or set them as environment variables:

```yaml
aws:
  s3:
    region: ${AWS_REGION:us-east-1}
    bucket-name: ${AWS_S3_BUCKET:lawyer-documents-bucket}
    endpoint: ${AWS_S3_ENDPOINT:http://localhost:9000} # Useful for local MinIO setup
    access-key: ${AWS_ACCESS_KEY_ID:minioadmin}
    secret-key: ${AWS_SECRET_ACCESS_KEY:minioadmin}
```
📡 API Overview (S3 Document Upload Flow)
To optimize server bandwidth, file uploads follow a two-step Presigned URL process:

[ Frontend ] --(1) Request Presigned URL--> [ Backend ]
[ Frontend ] <--(2) Return S3 Upload URL---- [ Backend ]
[ Frontend ] --(3) Direct PUT Upload------> [ AWS S3 / MinIO ]
[ Frontend ] --(4) Save Metadata DTO-------> [ Backend DB ]

1. Request Presigned Upload URL
POST /api/documents/presigned-url

Request Body:
`{
  "fileName": "contract_draft.pdf",
  "contentType": "application/pdf"
}`
Response:
`{
  "status": 200,
  "message": "Presigned URL generated successfully",
  "data": {
    "uploadUrl": "[https://bucket.s3.region.amazonaws.com/uuid_contract_draft.pdf?X-Amz-Algorithm=](https://bucket.s3.region.amazonaws.com/uuid_contract_draft.pdf?X-Amz-Algorithm=)...",
    "fileKey": "uuid_contract_draft.pdf",
    "fileUrl": "[https://bucket.s3.region.amazonaws.com/uuid_contract_draft.pdf](https://bucket.s3.region.amazonaws.com/uuid_contract_draft.pdf)"
  }
}`
3. Save Document Record
POST /api/documents

Request Body:
`{
  "title": "Initial Contract Draft",
  "description": "Signed employment agreement draft",
  "fileName": "contract_draft.pdf",
  "fileUrl": "[https://bucket.s3.region.amazonaws.com/uuid_contract_draft.pdf](https://bucket.s3.region.amazonaws.com/uuid_contract_draft.pdf)",
  "caseId": "case-uuid-here"
}`
🏃 Getting Started Locally
Prerequisites
JDK 21+

Maven

Docker (for local PostgreSQL or MinIO instances)

Run the Application
1-Clone the repository:
`git clone [https://github.com/Haidar-Issa/Lawyer_Backend-System.git](https://github.com/Haidar-Issa/Lawyer_Backend-System.git)
cd Lawyer_Backend-System`

2-Build the project:
`mvn clean install`

3-Start the application:
`mvn spring-boot:run`

📝 License
Distributed under the MIT License. See LICENSE for more information.
