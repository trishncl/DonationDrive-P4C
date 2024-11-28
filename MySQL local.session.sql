CREATE TABLE Donors (
    DonorId INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Address VARCHAR(255),
    PhoneNumber VARCHAR(15),
    Email VARCHAR(100) UNIQUE NOT NULL,
    Password VARCHAR(100) NOT NULL,
    Role VARCHAR(50) DEFAULT 'Donor',
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE organizations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE Monetary (
    Ref_number VARCHAR(15) PRIMARY KEY,
    DonorId INT,
    OrgId INT,
    Amount DECIMAL(10,2) NOT NULL,
    ModeOfPayment ENUM('PayMaya', 'GCash') NOT NULL,
    FOREIGN KEY (DonorId) REFERENCES Donors(DonorId),
    FOREIGN KEY (OrgId) REFERENCES organizations(id)
);

CREATE TABLE InKind (
    Ref_number VARCHAR(15) PRIMARY KEY,
    DonorId INT,
    OrgId INT,
    Item VARCHAR(255) NOT NULL,
    NoOfItems INT NOT NULL,
    Delivery ENUM('Standard Shipping', 'Freight Shipping', 'Drop-Off', 'J&T Express'),
    FOREIGN KEY (DonorId) REFERENCES Donors(DonorId),
    FOREIGN KEY (OrgId) REFERENCES organizations(id)
);

CREATE TABLE Donations (
    DonationId INT AUTO_INCREMENT PRIMARY KEY,
    MonetaryRef VARCHAR(50) NULL,
    InKindRef VARCHAR(50) NULL,
    DonorId INT,
    OrganizationId INT,
    DonationType ENUM('Monetary', 'In-kind') NOT NULL,  
    ModeOfPayment ENUM('PayMaya', 'GCash') NULL,
    Amount DECIMAL(10, 2) NULL,  
    Item VARCHAR(255) NULL,  
    NoOfItems INT NULL,  
    Delivery ENUM('Standard Shipping', 'Freight Shipping', 'Drop-Off', 'J&T Express') NULL,
    Status ENUM('Pending', 'Completed', 'Cancelled') DEFAULT 'Pending',  
    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    FOREIGN KEY (DonorId) REFERENCES Donors(DonorId),
    FOREIGN KEY (MonetaryRef) REFERENCES Monetary(Ref_number),  
    FOREIGN KEY (InKindRef) REFERENCES InKind(Ref_number),
    FOREIGN KEY (OrganizationId) REFERENCES organizations(id)
);

-- Sample record per table

INSERT INTO Donors (Name, Address, PhoneNumber, Email, Password, CreatedAt) VALUES
    ("Gertrude Agena", "San Pascual, Batangas City", "0964 234 5678", "gertrudeagena@gmail.com", "Gertrude#456", NOW()),
    ("Jenrick Magtaas", "Haligue Kanluran, Batangas City", "0935 345 6789", "magtaasjenrick@gmail.com", "Magtaas%789", NOW()),
    ("B-jork Rico", "Tinga Labac, Batangas City", "0943 456 7890", "bjorkrico@gmail.com", "Bjork&111", NOW()),
    ("Kate Arellano", "Simlong, Batangas City", "0921 567 8901", "arellanokate@gmail.com", "Kate*222", NOW()),
    ("Ashley Banaag", "Tulo Centro, Batangas City", "0956 678 9012", "ashleybanaag@gmail.com", "Ashley^333", NOW()),
    ("Aldriena Manalo", "Bauan, Batangas City", "0912 789 0123", "manaloaldriena@gmail.com", "Aldriena@444", NOW()),
    ("Annrie Rosales", "San Pascual, Batangas City", "0934 890 1234", "annrierosales@gmail.com", "Annrie#555", NOW()),
    ("Ken Magnaye", "Bauan, Batangas City", "0961 901 2345", "magnayeken@gmail.com", "Ken&666", NOW()),
    ("Poul Aranas", "Mahabang Parang, Batangas City", "0940 012 3456", "poularanas@gmail.com", "Poul*777", NOW());


INSERT INTO organizations (name) VALUES
    ("Philippine Animal Welfare Society"),
    ("Compassion and Responsibility for Animals"),
    ("Animal Kingdom Foundation"),
    ("Philippine Animal Rescue Team"),
    ("CARA Welfare Philippines"),
    ("Animal Kingdom Foundation, Inc."),
    ("Happy Animals Club"),
    ("People For The Ethical Treatment of Animals Asia"),
    ("Animal Welfare Coalition"),
    ("Philippine Society for the Prevention of Cruelty to Animals");

INSERT INTO Monetary VALUES
    ("REF-102605", 1, 6, 5000.00, "PayMaya"),
    ("REF-122504", 2, 5, 7000.00, "GCash"),
    ("REF-080104", 3, 7, 15000.00, "PayMaya"),
    ("REF-092904", 4, 9, 2500.00, "GCash"),
    ("REF-013105", 5, 1, 6000.00, "PayMaya"),
    ("REF-043005", 6, 4, 10000.00, "GCash"),
    ("REF-011405", 7, 3, 1500.00, "PayMaya"),
    ("REF-112205", 8, 2, 20000.00, "GCash"),
    ("REF-032405", 9, 8, 9000.00, "PayMaya"),
    ("REF-030205", 10, 10, 30000.00, "GCash");

INSERT INTO InKind VALUES
    ("REF-678521", 1, 3, "Dog Food Bags", 20, "Standard Shipping"),
    ("REF-459357", 2, 4, "Cat Litter", 15, "Freight Shipping"),
    ("REF-984204", 3, 8, "Dog Toys", 30, "Drop-Off"),
    ("REF-017635", 4, 10, "Cat Treats", 25, "J&T Express"),
    ("REF-854166", 5, 2, "Bedding for Pets", 10, "Standard Shipping"),
    ("REF-149875", 6, 1, "Leashes", 12, "Freight Shipping"),
    ("REF-234710", 7, 6, "Pet Bowls", 18, "Drop-Off"),
    ("REF-348979", 8, 7, "Flea Treatments", 5, "J&T Express"),
    ("REF-547003", 9, 5, "Kennels", 3, "Standard Shipping"),
    ("REF-785421", 10, 9, "Vaccination Supplies", 6, "Freight Shipping");


INSERT INTO Donations (MonetaryRef, InKindRef, DonorId, OrganizationId, DonationType, ModeOfPayment, Amount, Item, NoOfItems, Delivery, date_time) VALUES
    ("REF-102605", NULL, 1, 6, "Monetary", "PayMaya", 5000.00, NULL, NULL, NULL, NOW()),
    ("REF-122504", NULL, 2, 5, "Monetary", "GCash", 7000.00, NULL, NULL, NULL, NOW()),
    ("REF-080104", NULL, 3, 7, "Monetary", "PayMaya", 15000.00, NULL, NULL, NULL, NOW()),
    ("REF-092904", NULL, 4, 9, "Monetary", "GCash", 2500.00, NULL, NULL, NULL, NOW()),
    ("REF-013105", NULL, 5, 1, "Monetary", "PayMaya", 6000.00, NULL, NULL, NULL, NOW()),
    ("REF-043005", NULL, 6, 4, "Monetary", "GCash", 10000.00, NULL, NULL, NULL, NOW()),
    ("REF-011405", NULL, 7, 3, "Monetary", "PayMaya", 1500.00, NULL, NULL, NULL, NOW()),
    ("REF-112205", NULL, 8, 2, "Monetary", "GCash", 20000.00, NULL, NULL, NULL, NOW()),
    ("REF-032405", NULL, 9, 8, "Monetary", "PayMaya", 9000.00, NULL, NULL, NULL, NOW()),
    ("REF-030205", NULL, 10, 10, "Monetary", "GCash", 30000.00, NULL, NULL, NULL, NOW());
    (NULL, "REF-678521", 1, 3, "In-Kind", NULL, NULL, "Dog Food Bags", 20, "Standard Shipping", NOW()),
    (NULL, "REF-459357", 2, 4, "In-Kind", NULL, NULL, "Cat Litter", 15, "Freight Shipping", NOW()),
    (NULL, "REF-984204", 3, 8, "In-Kind", NULL, NULL, "Dog Toys", 30, "Drop-Off", NOW()),
    (NULL, "REF-017635", 4, 10, "In-Kind", NULL, NULL, "Cat Treats", 25, "J&T Express", NOW()),
    (NULL, "REF-854166", 5, 2, "In-Kind", NULL, NULL, "Bedding for Pets", 10, "Standard Shipping", NOW()),
    (NULL, "REF-149875", 6, 1, "In-Kind", NULL, NULL, "Leashes", 12, "Freight Shipping", NOW()),
    (NULL, "REF-234710", 7, 6, "In-Kind", NULL, NULL, "Pet Bowls", 18, "Drop-Off", NOW()),
    (NULL, "REF-348979", 8, 7, "In-Kind", NULL, NULL, "Flea Treatments", 5, "J&T Express", NOW()),
    (NULL, "REF-547003", 9, 5, "In-Kind", NULL, NULL, "Kennels", 3, "Standard Shipping", NOW()),
    (NULL, "REF-785421", 10, 9, "In-Kind", NULL, NULL, "Vaccination Supplies", 6, "Freight Shipping", NOW());









