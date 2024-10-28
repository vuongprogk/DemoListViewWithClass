package vn.edu.stu.demolistviewwithclass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import vn.edu.stu.demolistviewwithclass.model.Student;

public class MainActivity extends AppCompatActivity {
    ArrayList<Student> listStudent;
    ArrayAdapter<Student> studentArrayAdapter;
    ListView listView;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAdd();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                processEdit(i);
            }
        });
    }

    private void processEdit(int i) {
        if(i >= 0 && i <= listStudent.size()){
            Student student = listStudent.get(i);
            Intent intent = new Intent(
                    MainActivity.this,
                    EditStudent.class
            );
            intent.putExtra("student", student);
            launcher.launch(intent);
        }
    }

    private void processAdd() {
        Intent intent = new Intent(
                MainActivity.this,
                EditStudent.class);
        launcher.launch(intent);
    }

    private void addControls() {
        listStudent = new ArrayList<>();
        studentArrayAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                listStudent);
        listView = findViewById(R.id.lvStudent);
        listView.setAdapter(studentArrayAdapter);
        btnAdd = findViewById(R.id.btnAdd);
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == 100) {
                        assert o.getData() != null;
                        Student studentAdded = (Student) o.getData().getSerializableExtra("student");
                        listStudent.add(studentAdded);
                        studentArrayAdapter.notifyDataSetChanged();
                    } else if (o.getResultCode() == 101) {
                        assert o.getData() != null;
                        Student studentModified = (Student) o.getData().getSerializableExtra("student");
                        for (Student st :
                                listStudent) {
                            if (st.getId() == studentModified.getId()) {
                                st.setName(studentModified.getName());
                                st.setPoint(studentModified.getPoint());
                            }
                        }
                        studentArrayAdapter.notifyDataSetChanged();
                    }
                }
            }
    );
}