-- Create Account table
CREATE TABLE Account (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(50) UNIQUE,  -- Changed from VARCHAR to NVARCHAR
    password NVARCHAR(255),         -- Changed from VARCHAR to NVARCHAR
    email NVARCHAR(100) UNIQUE,     -- Changed from VARCHAR to NVARCHAR
    googleId NVARCHAR(100),         -- Changed from VARCHAR to NVARCHAR
    role NVARCHAR(10) DEFAULT N'USER', -- Make sure to use N'USER' for Unicode
    isActive BIT DEFAULT 1,
    createdAt DATETIME DEFAULT GETDATE(),
    lastLogin DATETIME,
    resetToken NVARCHAR(255),
    resetTokenExpiry DATETIME
);

-- Create Label table (City, Location)
CREATE TABLE Label (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    city NVARCHAR(100),               -- Ensuring NVARCHAR for Vietnamese
    location NVARCHAR(100),           -- Ensuring NVARCHAR for Vietnamese
    UNIQUE(city, location)            -- Ensure uniqueness if necessary
);

-- Create Post table (Complaint)
CREATE TABLE Post (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    accountId BIGINT,
    labelId BIGINT,
    content NVARCHAR(MAX),            -- Changed from TEXT to NVARCHAR(MAX) for better support
    status NVARCHAR(10) DEFAULT N'PENDING', -- Use N'PENDING' for Unicode
    createdAt DATETIME DEFAULT GETDATE(),
    updatedAt DATETIME DEFAULT GETDATE(),
    deletedAt DATETIME,               -- For soft delete
    FOREIGN KEY (accountId) REFERENCES Account(id),
    FOREIGN KEY (labelId) REFERENCES Label(id)
);

-- Create Setting table
CREATE TABLE Setting (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    [key] NVARCHAR(100) UNIQUE,       -- Key for different settings
    value NVARCHAR(255)               -- Ensuring NVARCHAR for potential Unicode values
);

-- Create Approval table
CREATE TABLE Approval (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    postId BIGINT,
    adminId BIGINT,
    status NVARCHAR(10) DEFAULT N'PENDING', -- Use N'PENDING' for Unicode
    approvalTime DATETIME DEFAULT GETDATE(),
    comment NVARCHAR(MAX),             -- Changed from TEXT to NVARCHAR(MAX)
    FOREIGN KEY (postId) REFERENCES Post(id),
    FOREIGN KEY (adminId) REFERENCES Account(id)
);

ALTER TABLE Account
ALTER COLUMN username NVARCHAR(50) COLLATE Vietnamese_CI_AS;

ALTER TABLE Label
ALTER COLUMN city NVARCHAR(100) COLLATE Vietnamese_CI_AS;

ALTER TABLE Label
ALTER COLUMN location NVARCHAR(100) COLLATE Vietnamese_CI_AS;

ALTER TABLE Post
ALTER COLUMN content NVARCHAR(MAX) COLLATE Vietnamese_CI_AS;

ALTER TABLE Setting
ALTER COLUMN [key] NVARCHAR(100) COLLATE Vietnamese_CI_AS;

ALTER TABLE Setting
ALTER COLUMN value NVARCHAR(255) COLLATE Vietnamese_CI_AS;

ALTER TABLE Approval
ALTER COLUMN comment NVARCHAR(MAX) COLLATE Vietnamese_CI_AS;



-- Insert sample data into Account table
INSERT INTO Account (username, password, email, googleId, role, isActive, createdAt) VALUES 
(N'jane_smith', N'hashed_password_2', N'jane.smith@example.com', NULL, N'USER', 1, GETDATE()),
(N'admin_user', N'hashed_password_3', N'admin@example.com', NULL, N'ADMIN', 1, GETDATE());

-- Insert sample data into Label table
INSERT INTO Label (city, location) VALUES 
(N'Hà Nội', N'Hoàn Kiếm'),
(N'Hồ Chí Minh', N'Quận 1'),
(N'Đà Nẵng', N'Sơn Trà'),
(N'Hải Phòng', N'Lê Chân'),
(N'Nha Trang', N'Vĩnh Hải');

-- Insert sample data into Post table
INSERT INTO Post (accountId, labelId, content, status, createdAt, updatedAt) VALUES 
(1, 1, N'Tôi muốn phàn nàn về tiếng ồn ở khu vực Hoàn Kiếm.', N'PENDING', GETDATE(), GETDATE()),
(2, 2, N'Tình trạng đỗ xe ở Quận 1 thật tồi tệ.', N'PENDING', GETDATE(), GETDATE()),
(1, 3, N'Tôi đã mất ví ở Sơn Trà, xin hãy giúp tôi!', N'PENDING', GETDATE(), GETDATE()),
(2, 4, N'Chất lượng dịch vụ đã giảm ở Lê Chân, Hải Phòng.', N'PENDING', GETDATE(), GETDATE()), -- Changed accountId from 3 to 2
(2, 5, N'Các con đường ở Vĩnh Hải, Nha Trang đang trong tình trạng kém.', N'PENDING', GETDATE(), GETDATE());

-- Insert sample data into Setting table
INSERT INTO Setting ([key], value) VALUES 
(N'site_name', N'Cổng Thông Tin Phàn Nàn'),
(N'support_email', N'support@complainportal.com'),
(N'max_upload_size', N'10MB'),
(N'maintenance_mode', N'false'),
(N'default_language', N'vi');

-- Insert sample data into Approval table
INSERT INTO Approval (postId, adminId, status, approvalTime, comment) VALUES 
(1, 2, N'APPROVED', GETDATE(), N'Đã được xử lý nhanh chóng.'), -- Changed adminId from 3 to 2
(2, 2, N'REJECTED', GETDATE(), N'Không phải là một phàn nàn hợp lệ.'), -- Changed adminId from 3 to 2
(3, 2, N'PENDING', GETDATE(), NULL), -- Changed adminId from 3 to 2
(4, 2, N'PENDING', GETDATE(), NULL), -- Changed adminId from 3 to 2
(5, 2, N'PENDING', GETDATE(), NULL); -- Changed adminId from 3 to 2










