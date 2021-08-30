package com.T_Y.controller;

import com.T_Y.model.User;
import com.T_Y.view.UserCustomizedScreen;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserManagement {
    private boolean isRealUser = false;
    private JFrame errorMessage;
    private JSONObject finalJson;

    public UserManagement() {
    }

    private JSONObject usernameCheck(User tempUser) {

        /**
         //this function will use the json schema to make sure the user values
         // are by the system rules (strong password)
         **/


        //getting the JSON schema into a JSONObject
        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(UserManagement.class.getResourceAsStream("/schema.json")));

        //the code in the try section will transform the user into
        // a string according to Json rules and later into a JSONObject
        //final step is using the schema to make sure the tempUser Json is legal
        //if illegal there will be an error thrown to us.
        //if legal it will just move on with the code.
        try {
            ObjectMapper mapper = new ObjectMapper();
            String tempJsonString = mapper.writeValueAsString(tempUser);


//
//            String jsonFinalString="{";
//            tempJsonString=tempJsonString.substring(11);//removing id=null
//            jsonFinalString=jsonFinalString+tempJsonString;
            System.out.println("ResultingJSONstring = " + tempJsonString);

            JSONObject jsonSubject = new JSONObject(new JSONTokener(tempJsonString));

            Schema schema = SchemaLoader.load(jsonSchema);
            schema.validate(jsonSubject);
            return jsonSubject;
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        catch (ValidationException e2)
        {
            //tells the user why he failed
            System.out.println(e2.getMessage());
        }
        return null;
    }

    public boolean registerUser(User tempUser)  {
        try {
            this.finalJson=usernameCheck(tempUser);
                if(finalJson!=null) {
                    System.out.println("User meets requirements");
                }
            else return false;
            if (new UsersDB().registerUserToDB(finalJson)) {
                JOptionPane.showMessageDialog(null, "Registration Succeeded", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginUser(User tempUser)  {
        try {
            if (new UsersDB().loginUserToDB(tempUser)) {
                JOptionPane.showMessageDialog(errorMessage, "Login Allowed", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                new UserCustomizedScreen(tempUser);
                return true;
            } else {
                JOptionPane.showMessageDialog(errorMessage, "Login failed", "Dialog", JOptionPane.ERROR_MESSAGE);
                //return false comes later
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }


}






