# Document Upload Implementation

This implementation allows users to upload background check documents through the BackgroundCheck screen and save them to the database.

## Backend Implementation

### 1. Database Entities

#### Document Entity (`Document.java`)
- Stores uploaded document information
- Fields:
  - `documentId` - Auto-generated primary key
  - `documentType` - Type of document (Reference, Police Clearance, Driver Test, Drivers License)
  - `fileName` - Original file name
  - `fileUrl` - Optional file URL
  - `mimeType` - File content type
  - `fileSize` - File size in bytes
  - `fileData` - Binary file data (stored as BLOB)
  - `uploadDate` - When the document was uploaded
  - `uploadedBy` - User who uploaded the document
  - `status` - Document status (Pending, Approved, Rejected)
  - `backgroundCheck` - Reference to BackgroundCheck entity

#### BackgroundCheck Entity (Updated)
- Added `documents` field - One-to-Many relationship with Document

### 2. Repository

**DocumentRepository.java**
- JPA repository for Document entity
- Custom queries:
  - `findByBackgroundCheckBackgroundCheckId()` - Get all documents for a background check
  - `findByDocumentType()` - Get documents by type
  - `findByStatus()` - Get documents by status

### 3. Service Layer

**IDocumentService.java** & **DocumentServiceImpl.java**
- CRUD operations for documents
- Additional methods for querying documents

### 4. Controller

**DocumentController.java**
API Endpoints:

#### Upload Single Document
```
POST /api/documents/upload
Parameters:
- file: MultipartFile
- documentType: String
- backgroundCheckId: Integer
- uploadedBy: String (optional)
```

#### Upload Multiple Documents
```
POST /api/documents/upload-multiple
Parameters:
- files: List<MultipartFile>
- documentTypes: List<String>
- backgroundCheckId: Integer
- uploadedBy: String (optional)
```

#### Download Document
```
GET /api/documents/download/{id}
Returns: File as byte array
```

#### Get Documents by Background Check
```
GET /api/documents/background-check/{backgroundCheckId}
Returns: List of documents
```

#### Update Document Status
```
PUT /api/documents/update-status/{id}?status={status}
```

#### Delete Document
```
DELETE /api/documents/delete/{id}
```

### 5. Configuration

**application.properties**
```properties
# File Upload Configuration
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=15MB
spring.servlet.multipart.file-size-threshold=2KB
```

## Frontend Implementation (React Native)

### Updated Components

**BackgroundCheck.jsx**

#### Features:
1. **Document Selection**: Users can select documents using expo-document-picker
2. **File Storage**: Selected files are stored temporarily with their URIs
3. **Backend Upload**: On submit, files are uploaded to the backend via FormData
4. **Progress Tracking**: Shows which documents have been uploaded
5. **Error Handling**: Displays errors if upload fails

#### Key Functions:

**handleFileUpload(itemId)**
- Opens document picker
- Stores file information locally
- Updates UI to show file selected

**uploadDocumentsToBackend()**
- Iterates through all selected documents
- Creates FormData for each document
- Uploads to backend API
- Returns success/failure status

**handleSubmit()**
- Validates all required documents are uploaded
- Calls uploadDocumentsToBackend()
- Shows success message and navigates to next screen

### Configuration

Update the API_BASE_URL in BackgroundCheck.jsx:
```javascript
const API_BASE_URL = "http://your-server-ip:8080/api";
```

For Android emulator: `http://10.0.2.2:8080/api`
For iOS simulator: `http://localhost:8080/api`
For physical device: `http://YOUR_COMPUTER_IP:8080/api`

### Dependencies

Added to package.json:
```json
"expo-file-system": "~18.1.6"
```

Install with:
```bash
cd WeCare
npm install
```

## Usage Flow

1. **User navigates to Background Check screen**
2. **User clicks on each document type** (Reference, Police Clearance, etc.)
3. **Document picker opens** - User selects a file
4. **File is stored locally** - UI updates to show "Uploaded ✓"
5. **After all documents are selected** - User clicks Submit
6. **Documents are uploaded** to backend one by one
7. **Backend saves documents** to database with file data
8. **Success message shown** - User navigates to next screen

## Database Schema

The Document table will be created automatically with these columns:
- document_id (INT, PRIMARY KEY, AUTO_INCREMENT)
- document_type (VARCHAR)
- file_name (VARCHAR)
- file_url (VARCHAR)
- mime_type (VARCHAR)
- file_size (BIGINT)
- file_data (LONGBLOB)
- upload_date (DATETIME)
- uploaded_by (VARCHAR)
- status (VARCHAR)
- background_check_id (INT, FOREIGN KEY)

## Testing

### Backend Testing:
1. Use Postman or similar tool
2. Send POST request to `/api/documents/upload`
3. Attach a file and required parameters
4. Check database for saved document

### Frontend Testing:
1. Run the React Native app
2. Navigate to Background Check screen
3. Click "Test: Simulate All Uploads" button for quick testing
4. Or select real files and click Submit
5. Check console for upload logs

## Security Considerations

1. **File Size Limits**: Configured in application.properties (10MB max)
2. **File Type Validation**: Frontend validates file types
3. **Backend Validation**: Add additional validation in controller if needed
4. **Database Storage**: Files stored as BLOB (consider file system storage for production)
5. **Authentication**: Add user authentication before allowing uploads

## Future Enhancements

1. Store files in file system instead of database for better performance
2. Add file encryption for sensitive documents
3. Add virus scanning before upload
4. Implement document preview functionality
5. Add admin panel for document approval/rejection
6. Add notifications when documents are reviewed
