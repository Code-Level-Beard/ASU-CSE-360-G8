Starting on line  61-62 in the sampleController.java file you will need to change the file directory where ever you store the file at. Below is the section 
where you will need to change the directory for credentials.txt.


  private boolean validateCredentials(String enteredUsername, String enteredPassword) {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jaron\\OneDrive\\Desktop\\CSE_360_IntroSoftware_Engineering\\Program_Files\\GroupProject_SceneBuilder_LoginPanel_Backup\\src\\application\\credentials.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String storedUsername = parts[0];
                String storedPassword = parts[1];

                if (enteredUsername.equals(storedUsername) && enteredPassword.equals(storedPassword)) {
                    return true; // Valid credentials
                }
                
                
 ***** Login Credentials Guide ******
 
 To Login as Doctor: username1 , password1
 To Login as Nurse: username2 , password2
 To Login as Patient: username3 , password3
 
                