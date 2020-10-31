package com.avarice.webservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Student> students;

    public StudentAdapter(Context context, int layout, List<Student> students) {
        this.context = context;
        this.layout = layout;
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder {
        private TextView txtName, txtBorn, txtAddress;
        private ImageView btnEdit, btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.txtName = convertView.findViewById(R.id.texViewName);
            holder.txtBorn = convertView.findViewById(R.id.textViewBorn);
            holder.txtAddress = convertView.findViewById(R.id.textViewAddress);
            holder.btnDelete = convertView.findViewById(R.id.buttonDelete);
            holder.btnEdit = convertView.findViewById(R.id.buttonEdit);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Student student = students.get(position);
        holder.txtName.setText(student.getName());
        holder.txtBorn.setText(String.valueOf(student.getBorn()));
        holder.txtAddress.setText(student.getAddress());

        return convertView;
    }
}
