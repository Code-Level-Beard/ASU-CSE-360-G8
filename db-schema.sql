BEGIN TRANSACTION;
DROP TABLE IF EXISTS "Login";
CREATE TABLE IF NOT EXISTS "Login" (
	"user_id"	STRING,
	"password"	STRING NOT NULL,
	"user_type"	STRING NOT NULL,
	PRIMARY KEY("user_id")
);
DROP TABLE IF EXISTS "UserType";
CREATE TABLE IF NOT EXISTS "UserType" (
	"user_id"	STRING,
	"user_type"	STRING,
	"first_name"	STRING,
	"last_name"	STRING,
	PRIMARY KEY("user_id")
);
DROP TABLE IF EXISTS "Visit";
CREATE TABLE IF NOT EXISTS "Visit" (
	"patient_id"	STRING,
	"doctor_id"	STRING,
	"date"	STRING,
	"height"	STRING,
	"weight"	STRING,
	"temperature"	STRING,
	"blood_pressure"	STRING,
	"immunization"	STRING,
	"allergies"	STRING,
	"notes"	STRING,
	"prescription"	STRING,
	"visit_diag"	STRING,
	"completed"	STRING
);
DROP TABLE IF EXISTS "Message";
CREATE TABLE IF NOT EXISTS "Message" (
	"patient_id"	STRING,
	"message_id"	STRING,
	"sender"	STRING,
	"recipients"	STRING,
	"header"	STRING,
	"content"	STRING
);
DROP TABLE IF EXISTS "PatientRecord";
CREATE TABLE IF NOT EXISTS "PatientRecord" (
	"patient_id"	STRING,
	"first_name"	STRING,
	"last_name"	STRING,
	"address"	STRING,
	"phone_number"	STRING,
	"ins_id"	STRING,
	"pharmacy"	STRING,
	"health_history"	STRING,
	"immunizations"	STRING,
	"medications"	STRING,
	"allergies"	STRING,
	"assigned_doctor"	STRING,
	"DOB"	STRING,
	PRIMARY KEY("patient_id")
);
INSERT INTO "Login" VALUES ('MD1234','MD1234','Physician');
INSERT INTO "Login" VALUES ('NR1234','NR1234','Nurse');
INSERT INTO "Login" VALUES ('PT1234','PT1234','Patient');
INSERT INTO "Login" VALUES ('JD0119','JD0119','Patient');
INSERT INTO "Login" VALUES ('JD1256','JD1256','Physician');
INSERT INTO "Login" VALUES ('JS1298','JS1298','Physician');
INSERT INTO "Login" VALUES ('BB0918','BB0918','Patient');
INSERT INTO "Login" VALUES ('JW0470','JW0470','Nurse');
INSERT INTO "Login" VALUES ('MC0117','MC0117','Physician');
INSERT INTO "Login" VALUES ('AS3475','AS3475','Patient');
INSERT INTO "Login" VALUES ('TD4298','TD4298','Nurse');
INSERT INTO "Login" VALUES ('SF2157','SF2157','Patient');
INSERT INTO "Login" VALUES ('as2259','as2259','Patient');
INSERT INTO "UserType" VALUES ('JD1256','Physician','Jane','Doe');
INSERT INTO "UserType" VALUES ('JS1298','Physician','Joe','Smith');
INSERT INTO "UserType" VALUES ('BB0918','Patient','Bob','Builder');
INSERT INTO "UserType" VALUES ('JW0470','Nurse','John','Wick');
INSERT INTO "UserType" VALUES ('MC0117','Physician','Master','Chief');
INSERT INTO "UserType" VALUES ('JD0119','Patient','Josh','Decker');
INSERT INTO "UserType" VALUES ('AS3475','Patient','Agent','Smith');
INSERT INTO "UserType" VALUES ('TD4298','Nurse','Tyler','Durden');
INSERT INTO "UserType" VALUES ('SF2157','Patient','Sally','Fields');
INSERT INTO "UserType" VALUES ('as2259','Patient','asld;fjksdf','sa''dkjg''sk');
INSERT INTO "Message" VALUES ('JD0119',1,'Doctor','','read','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.');
INSERT INTO "Message" VALUES ('JD0119',2,'Nurse',NULL,'read','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.');
INSERT INTO "Message" VALUES ('JD0119',3,'Patient',NULL,'read','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.');
INSERT INTO "Message" VALUES ('SF2157',1,'SF','','read','Message 1');
INSERT INTO "Message" VALUES ('SF2157',2,'SF',NULL,'read','Message 2');
INSERT INTO "Message" VALUES ('SF2157',3,'SF',NULL,'read','Message 3');
INSERT INTO "Message" VALUES ('SF2157',4,'SF',NULL,'read','Message 4');
INSERT INTO "Message" VALUES ('JD0119',4,'Nurse',NULL,'read','Hello! I would be happy to help!');
INSERT INTO "Message" VALUES ('JD0119',5,'Nurse',NULL,'read','Awesome!');
INSERT INTO "Message" VALUES ('JD0119',6,'Nurse',NULL,'read','Just let me know where to send it!');
INSERT INTO "Message" VALUES ('JD0119',7,'Josh Decker',NULL,'read','Okay!');
INSERT INTO "Message" VALUES ('JD0119',8,'Josh Decker',NULL,'read','It is working!');
INSERT INTO "Message" VALUES ('AS3475',1,'Agent Smith',NULL,'read','I need help!');
INSERT INTO "Message" VALUES ('AS3475',2,'Nurse',NULL,'read','What do you need?');
INSERT INTO "Message" VALUES ('AS3475',3,'Nurse',NULL,'read','We are here for you 24/7!');
INSERT INTO "Message" VALUES ('AS3475',4,'Nurse',NULL,'read','Hmmmmm');
INSERT INTO "Message" VALUES ('AS3475',5,'Nurse',NULL,'read','Why');
INSERT INTO "Message" VALUES ('AS3475',6,'Agent Smith',NULL,'read','How are you?');
INSERT INTO "Message" VALUES ('AS3475',7,'Agent Smith',NULL,'read','Awesome?');
INSERT INTO "Message" VALUES ('AS3475',8,'John Wick',NULL,'read','Hello, my name is John Wick');
INSERT INTO "Message" VALUES ('AS3475',9,'John Wick',NULL,'read','Yay');
INSERT INTO "Message" VALUES ('JD0119',9,'John Wick',NULL,'read','Nice to meet you');
INSERT INTO "Message" VALUES ('JD0119',10,'Josh Decker',NULL,'read','Nice to meet you too Mr. Wick');
INSERT INTO "Message" VALUES ('JD0119',11,'John Wick',NULL,'read','I need guns...LOT''S of guns!');
INSERT INTO "Message" VALUES ('JD0119',12,'Josh Decker',NULL,'read','If you want peace Mr. WIck, best prepare for war!');
INSERT INTO "Message" VALUES ('SF2157',5,'John Wick',NULL,'read','Message 5');
INSERT INTO "Message" VALUES ('SF2157',6,'John Wick',NULL,'read','A long message');
INSERT INTO "Message" VALUES ('SF2157',7,'John Wick',NULL,'read','Let me see if scrolling is working');
INSERT INTO "Message" VALUES ('SF2157',8,'John Wick',NULL,'read','Okay');
INSERT INTO "Message" VALUES ('SF2157',9,'John Wick',NULL,'read','Almost there');
INSERT INTO "Message" VALUES ('SF2157',10,'John Wick',NULL,'read','Nice');
INSERT INTO "Message" VALUES ('AS3475',10,'Agent Smith',NULL,'read','Nice');
INSERT INTO "Message" VALUES ('AS3475',11,'Agent Smith',NULL,'read','? what ?');
INSERT INTO "Message" VALUES ('AS3475',12,'Agent Smith',NULL,'read','Nice');
INSERT INTO "Message" VALUES ('AS3475',13,'Agent Smith',NULL,'read','Nice again');
INSERT INTO "Message" VALUES ('AS3475',14,'Agent Smith',NULL,'read','Awesome');
INSERT INTO "Message" VALUES ('AS3475',15,'Agent Smith',NULL,'read','Cool');
INSERT INTO "Message" VALUES ('AS3475',16,'Agent Smith',NULL,'read','It is working');
INSERT INTO "Message" VALUES ('AS3475',17,'John Wick',NULL,'read','Nice');
INSERT INTO "Message" VALUES ('SF2157',11,'John Wick',NULL,'read','Hello Sally');
INSERT INTO "Message" VALUES ('JD0119',13,'John Wick',NULL,'read','Hello Josh');
INSERT INTO "Message" VALUES ('SF2157',12,'Master Chief',NULL,'read','Hello Sally');
INSERT INTO "Message" VALUES ('SF2157',13,'Master Chief',NULL,'read','This is Doctor Master Chief');
INSERT INTO "Message" VALUES ('BB0918',1,'Master Chief',NULL,'read','Hello Bob');
INSERT INTO "Message" VALUES ('BB0918',2,'Master Chief',NULL,'read','Dr. Master Chief here');
INSERT INTO "Message" VALUES ('BB0918',3,'Master Chief',NULL,'read','M.D.');
INSERT INTO "PatientRecord" VALUES ('JD0119','Josh','Decker','1234 Street Tempe, AZ','480-000-0119','Aetna 123456789','CVS Tempe','Left Knee Pain','Fair weather fans','Aspirin 400mg','LA Fans','JD1256','11/05/1988');
INSERT INTO "PatientRecord" VALUES ('AS3475','Agent','Smith','The Matirx','480-789-1234','Blue Cross 527895','3/31/1999','Tendency to split','The One','Red Pill','Neo','JS1298','3/31/1999');
INSERT INTO "PatientRecord" VALUES ('BB0918','Bob','Builder','1234 Cartoon Lane Tempe, Az','480-123-4567','HomeDepot 123456789','Walgreens Mesa','Headache','Tetanus','None','Unbuilt Stuff','MC0117','4/12/1999');
INSERT INTO "PatientRecord" VALUES ('JM2103','Joe','McTester','Imaginary Land','480-456-4567','Aetna 123456789','Costco Chandler','N/A','TDAP 2024','N/A','Bugs','MC0117','4/7/2024');
INSERT INTO "PatientRecord" VALUES ('SF2157','Sally','Fields','123 Cookie Cutter Lane Tempe, Az 85284','480-266-5437','BlueCross 123456789','Walmart','Diabetes','N/A','Insulin 20mg','Hard Cookies','MC0117','04/20/1969');
INSERT INTO "PatientRecord" VALUES ('as2259','asld;fjksdf','sa''dkjg''sk','Test Tempe,Az 85301','480-123-4567','Aetna 123456789','Walgreens','N/A','N/A','N/A','N/A','JS1298','4/8/2023');
COMMIT;
