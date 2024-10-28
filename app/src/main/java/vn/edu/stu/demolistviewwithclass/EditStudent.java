package vn.edu.stu.demolistviewwithclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.stu.demolistviewwithclass.model.Student;

public class EditStudent extends AppCompatActivity {

    EditText edtId, edtName, edtPoint;
    Button btnExit, btnSave;
    Student student = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("student")) {
            student = (Student) intent.getSerializableExtra("student");
            assert student != null;
            edtId.setText(String.valueOf(student.getId()));
            
            edtName.setText(String.valueOf(student.getName()));
            edtPoint.setText(String.valueOf(student.getPoint()));
        }
    }

    private void addEvents() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processExit();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processSave();
            }
        });
    }

    private void processSave() {
        if (student == null){
            int id = Integer.parseInt(edtId.getText().toString());
            String name = edtName.getText().toString();
            double point = Double.parseDouble(edtPoint.getText().toString());
            Student st = new Student(id,name,point);
            Intent intent = new Intent();
            intent.putExtra("student", st);
            setResult(
                    100, // 100 is add status code
                    intent);
        }
        else{
            String name = edtName.getText().toString();
            double point = Double.parseDouble(edtPoint.getText().toString());
            student.setName(name);
            student.setPoint(point);
            Intent intent = new Intent();
            intent.putExtra("student", student);
            setResult(
                    101, // 101 is modify status code
                    intent);
        }
        finish();
    }

    private void processExit() {
        finish();
    }

    private void addControls() {
        edtId = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtPoint = findViewById(R.id.edtPoint);
        btnSave = findViewById(R.id.btnSave);
        btnExit = findViewById(R.id.btnExit);
    }
}