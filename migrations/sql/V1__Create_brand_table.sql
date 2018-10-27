CREATE TABLE IF NOT EXISTS brands (
    BrandID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    BrandName VARCHAR(100) NOT NULL UNIQUE,
    TypeID INT NOT NULL,
    ContactDetailsID INT,
    UserID INT NOT NULL
);

CREATE TABLE IF NOT EXISTS vendor (
    UserID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    UserName VARCHAR(100) NOT NULL UNIQUE,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(100) NOT NULL,
    InsertionDate TIMESTAMP NOT NULL,
    LastAccessedDate TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS user_account (
    UserID INT NOT NULL PRIMARY KEY AUTO_INCREMENT.
    UserName VARCHAR(100) NOT NULL UNIQUE,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(100) NOT NULL,
    InsertionDate TIMESTAMP NOT NULL,
    LastAccessedDate TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS brand_type (
    TypeID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    TypeName VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS brand_contact_details (
    ContactID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    PhoneNumber VARCHAR(100),
    Address VARCHAR(100)
);

ALTER TABLE brands ADD FOREIGN KEY (TypeID) REFERENCES brand_type(TypeID);
ALTER TABLE brands ADD FOREIGN KEY (UserID) REFERENCES vendor(UserID);
ALTER TABLE brands ADD FOREIGN KEY (ContactDetailsID) REFERENCES brand_contact_details(ContactID);

-- adding default resturant types to table
INSERT INTO brand_type (TypeName) VALUE ("Bistro");
INSERT INTO brand_type (TypeName) VALUE ("Fast food");
INSERT INTO brand_type (TypeName) VALUE ("Casual Dining");
INSERT INTO brand_type (TypeName) VALUE ("Fine Dining");
INSERT INTO brand_type (TypeName) VALUE ("Barbecue");
INSERT INTO brand_type (TypeName) VALUE ("Ethnic");
INSERT INTO brand_type (TypeName) VALUE ("Café");