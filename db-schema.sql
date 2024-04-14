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
INSERT INTO "Login" VALUES ('TT3725','TT3725','Patient');
INSERT INTO "UserType" VALUES ('JD1256','Physician','Jane','Doe');
INSERT INTO "UserType" VALUES ('JS1298','Physician','Joe','Smith');
INSERT INTO "UserType" VALUES ('BB0918','Patient','Bob','Builder');
INSERT INTO "UserType" VALUES ('JW0470','Nurse','John','Wick');
INSERT INTO "UserType" VALUES ('MC0117','Physician','Master','Chief');
INSERT INTO "UserType" VALUES ('JD0119','Patient','Joshua','Decker');
INSERT INTO "UserType" VALUES ('AS3475','Patient','Agent','Smith');
INSERT INTO "UserType" VALUES ('TD4298','Nurse','Tyler','Durden');
INSERT INTO "UserType" VALUES ('SF2157','Patient','Sally','Fields');
INSERT INTO "UserType" VALUES ('as2259','Patient','asld;fjksdf','sa''dkjg''sk');
INSERT INTO "UserType" VALUES ('TT3725','Patient','TEST JM','TEST JM');
INSERT INTO "Visit" VALUES ('AS3475','JS1298','08/07/1998',6,7,34,234,'Goats','Fvvdsws','Gamers','Dogs','Too Sauced','True');
INSERT INTO "Visit" VALUES ('AS3475','JS1298','78/78/7654','10''11',340,456,456,'Godzilla','KingKong','He is Him.','Prozac','Ketchup','c');
INSERT INTO "Visit" VALUES ('SF2157','MC0117','4/12/2024','6''2',11,2,2,2,2,'NOTE.NOTE.NOTE',NULL,NULL,NULL);
INSERT INTO "Visit" VALUES ('SF2157','MC0117','4/13/2024',6,3,3,3,3,3,'NOTES,NOTES,NOTES',NULL,NULL,NULL);
INSERT INTO "Visit" VALUES ('SF2157','MC0117','4/14/2025',666666666,'ASDFASDF','ASDFASDFASDF','ASDFASDFASDF','ASDFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF','ASDFASDFASDFASDFAFDAF','D',NULL,NULL,NULL);
INSERT INTO "Visit" VALUES ('SF2157','MC0117','ADFASF','ADFASF','ASDFASF','ASDFASF','ADFASF','ASDFAF','ASDFAS','D
D
D
D
D
D
DD
D

DD
D
D
D

DD
D
D
D
D
D
D
DD

D
D
D
D',NULL,NULL,NULL);
INSERT INTO "Visit" VALUES ('BB0918','MC0117','4/12/2024','6 '' 3',111,222,22222,'1. Imunization 1. Imunization 1. Imunization 1. Imunization
1. Imunization 1. Imunization 1. Imunization 1. Imunization 
1. Imunization 1. Imunization 1. Imunization','1. allergy  1. allergy  1. allergy  1. allergy  1. allergy  1. allergy 
1. allergy  1. allergy  1. allergy  1. allergy  
1. allergy  1. allergy','dssdfsfsdfasdfsf','1.) Hammer nails 10x per each hour
2.) Saw 5 boards x each day
3.) Hammer nails 10x per each hour
Hammer nails 10x per each hour
Hammer nails 10x per each hour
Hammer nails 10x per each hour','Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis','C');
INSERT INTO "Visit" VALUES ('BB0918','MC0117','4/12/2024','6 '' 3',111,222,22222,'1. Imunization 1. Imunization 1. Imunization 1. Imunization
1. Imunization 1. Imunization 1. Imunization 1. Imunization 
1. Imunization 1. Imunization 1. Imunization','1. allergy  1. allergy  1. allergy  1. allergy  1. allergy  1. allergy 
1. allergy  1. allergy  1. allergy  1. allergy  
1. allergy  1. allergy','dssdfsfsdfasdfsf','1.) Hammer nails 10x per each hour
2.) Saw 5 boards x each day
3.) Hammer nails 10x per each hour
Hammer nails 10x per each hour
Hammer nails 10x per each hour
Hammer nails 10x per each hour','Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis','C');
INSERT INTO "Visit" VALUES ('BB0918',NULL,'4/12/2024','5''0',1,2,3,'1. Imunization 1. Imunization 1. Imunization 1. Imunization
1. Imunization 1. Imunization 1. Imunization 1. Imunization 
1. Imunization 1. Imunization 1. Imunization','1. allergy  1. allergy  1. allergy  1. allergy  1. allergy  1. allergy 
1. allergy  1. allergy  1. allergy  1. allergy  
1. allergy  1. allergy','dssdfsfsdfasdfsf
dssdfsfsdfasdfsf
dssdfsfsdfasdfsf
dssdfsfsdfasdfsfdssdfsfsdfasdfsf
dssdfsfsdfasdfsf
dssdfsfsdfasdfsf
','1.) Hammer nails 10x per each hour
2.) Saw 5 boards x each day
3.) Hammer nails 10x per each hour
Hammer nails 10x per each hour
Hammer nails 10x per each hour
Hammer nails 10x per each hour','Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis
Has constructionitis','C');
INSERT INTO "Visit" VALUES ('BB0918',NULL,'4/12/2024','TEST NC function','TEST NC function','TEST NC function','TEST NC function','','','........','
','','NC');
INSERT INTO "Visit" VALUES ('TT3725','MC0117','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM
TEST JM
TEST JM
TEST JM
TEST JM
TEST JM
TEST JM','TEST JM
TEST JMTEST JM
TEST JM
TEST JMTEST JMTEST JM

TEST JM
TEST JM
TEST JM','TEST JM',NULL,NULL,NULL);
INSERT INTO "Visit" VALUES ('TT3725',NULL,'TEST JM','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM
TEST JM
TEST JM
TEST JM
TEST JM
TEST JM
TEST JM','TEST JM
TEST JMTEST JM
TEST JM
TEST JMTEST JMTEST JM

TEST JM
TEST JM
TEST JM','TEST JM','TEST JM
TEST JM
TEST JM
TEST JM
TEST JM','
TEST JM
TEST JM
TEST JM
TEST JM
TEST JM','C');
INSERT INTO "Visit" VALUES ('TT3725','your_doctor_id','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM
TEST JM
TEST JM
TEST JM
TEST JM
TEST JM
TEST JM','TEST JM
TEST JMTEST JM
TEST JM
TEST JMTEST JMTEST JM

TEST JM
TEST JM
TEST JM','TEST JM','TEST JMTEST JM
','TEST JMTEST JM','C');
INSERT INTO "Visit" VALUES ('TT3725','MC0117','TEST UPDATE / DEBUG','TEST UPDATE / DEBUG','TEST UPDATE / DEBUG','TEST UPDATE / DEBUG','TEST UPDATE / DEBUG','TEST JM
TEST JM
TEST JM
TEST JM
TEST JM
TEST JM
TEST JM','TEST JM
TEST JMTEST JM
TEST JM
TEST JMTEST JMTEST JM

TEST JM
TEST JM
TEST JM','TEST UPDATE / DEBUG','TEST UPDATE / DEBUG
TEST UPDATE / DEBUG
TEST UPDATE / DEBUG
','TEST UPDATE / DEBUG
TEST UPDATE / DEBUG
TEST UPDATE / DEBUG','C');
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
INSERT INTO "Message" VALUES ('AS3475',10,'Tyler Durden',NULL,'read','Testing');
INSERT INTO "PatientRecord" VALUES ('JD0119','Joshua','Decker','1234 Street Tempe, AZ','480-000-0119','Aetna 123456789','CVS Tempe','Left Knee Pain','Fair weather fans','Aspirin 400mg, Tylenol 500mg','LA Fans','JD1256','11/05/1988');
INSERT INTO "PatientRecord" VALUES ('AS3475','Agent','Smith','The Matirx','480-789-1234','Blue Cross 527895','3/31/1999','Tendency to split','The One','Red Pill','Neo','JS1298','3/31/1999');
INSERT INTO "PatientRecord" VALUES ('BB0918','Bob','Builder','1234 Cartoon Lane Tempe, Az','480-123-4567','HomeDepot 123456789','Walgreens Mesa','Headache','Tetanus','None','Unbuilt Stuff','MC0117','4/12/1999');
INSERT INTO "PatientRecord" VALUES ('JM2103','Joe','McTester','Imaginary Land','480-456-4567','Aetna 123456789','Costco Chandler','N/A','TDAP 2024','N/A','Bugs','MC0117','4/7/2024');
INSERT INTO "PatientRecord" VALUES ('SF2157','Sally','Fields','123 Cookie Cutter Lane Tempe, Az 85284','480-266-5437','BlueCross 123456789','Walmart','Diabetes','N/A','Insulin 20mg','Hard Cookies','MC0117','04/20/1969');
INSERT INTO "PatientRecord" VALUES ('as2259','asld;fjksdf','sa''dkjg''sk','Test Tempe,Az 85301','480-123-4567','Aetna 123456789','Walgreens','N/A','N/A','N/A','N/A','JS1298','4/8/2023');
INSERT INTO "PatientRecord" VALUES ('TT3725','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM','TEST JM','MC0117','TEST JM');
COMMIT;
