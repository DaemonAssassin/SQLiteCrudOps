package com.gmail.mateendev3.androiddatabasev2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.mateendev3.androiddatabasev2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //declaring members
    ActivityMainBinding mBinding;
    DBHelper mHelper;
    EditText etName, etAge, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        //Instantiating member
        mHelper = new DBHelper(this);
        etName = mBinding.etName;
        etAge = mBinding.etAge;
        etEmail = mBinding.etEmail;

        //setting click listeners to buttons
        setListenersOnButtons();
    }

    private void setListenersOnButtons() {
        //setting listener to Add Button to insert data
        insertData();
        //setting listener to View Button to view data
        viewData();
        //setting listener to Delete Button to delete data
        deleteData();
        //setting listener to Update Button to update data
        updateData();

    }

    /**
     * Method to updated data form db using btn Update
     */
    private void updateData() {
        mBinding.btnUpdateRecord.setOnClickListener(v -> {
            String sAge = etAge.getText().toString();
            String email = etEmail.getText().toString();
            String name = etName.getText().toString();

            if (sAge.isEmpty() || email.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(sAge);
            boolean isUpdated = mHelper.updateData(name, email, age);
            if (isUpdated)
                Toast.makeText(this, "Succeed", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Not Succeed", Toast.LENGTH_SHORT).show();

            //resetting ets
            etName.setText("");
            etAge.setText("");
            etEmail.setText("");
        });
    }

    /**
     * method to show data to the user using btn view
     */
    private void viewData() {
        mBinding.btnViewData.setOnClickListener(v -> {
            //creating cursor object
            Cursor cursor = mHelper.getData();

            //condition if no data found
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder sb = new StringBuilder();
            //Loop to traverse from row 1 to last
            while (cursor.moveToNext()) {
                sb.append("Name: ").append(cursor.getString(1)).append("\n");
                sb.append("Age: ").append(cursor.getInt(2)).append("\n");
                sb.append("Email: ").append(cursor.getString(3)).append("\n\n\n");
            }

            //AlertDialog to show data to the user
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Student Data")
                    .setMessage(sb)
                    .create();
            dialog.show();
        });
    }

    /**
     * Method to insert data to db using btn add
     */
    private void insertData() {
        mBinding.btnAddRecord.setOnClickListener(v -> {
            String name = mBinding.etName.getText().toString();
            String sAge = mBinding.etAge.getText().toString();
            String email = mBinding.etEmail.getText().toString();

            if (name.isEmpty() || sAge.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(sAge);

            //inserting value to DB
            boolean areDataInserted = mHelper.insertData(name, age, email);
            if (areDataInserted)
                Toast.makeText(this, "Succeed", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();

            //resetting ets
            etName.setText("");
            etAge.setText("");
            etEmail.setText("");
        });
    }

    /**
     * Method to deleted data from db using btn Remove
     */
    private void deleteData() {
        mBinding.btnRemoveRecord.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            if (!email.isEmpty()) {
                boolean isDeleted = mHelper.deleteData(email);
                if (isDeleted)
                    Toast.makeText(this, "Deleted Succeed", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Deleted Not Succeed", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Please add email to remove data", Toast.LENGTH_SHORT).show();
            etEmail.setText("");
        });
    }
}