package com.example.p3b_tubes_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.Inflater;

public class AutocompleteAdapter extends ArrayAdapter {
    private ArrayList<String> arrayListNama;
    private Filter filter;

    public AutocompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.arrayListNama = new ArrayList<>();
        init();

        this.filter = instantiateFilter();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.autocomplete_item, parent, false);
        }

        String nama = getItem(position).toString();
        TextView item = convertView.findViewById(R.id.tv_autocomplete_item);
        if(getItem(position) != null) {;
            item.setText(nama);
        }

        return convertView;
    }

    private Filter instantiateFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<String> suggestions = new ArrayList<>();

                if(constraint == null || constraint.length() == 0 || constraint.equals("")) {
                    suggestions.addAll(arrayListNama);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for(String item : arrayListNama) {
                        if(item.toLowerCase().contains(filterPattern)) {
                            suggestions.add(item);
                        }
                    }
                }

                filterResults.values = suggestions;
                filterResults.count = suggestions.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((ArrayList<String>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    private void init() {
        this.arrayListNama.add("Keenan A. Leman, S.T., M.T.");
        this.arrayListNama.add("Raymond Chandra Putra, S.T., M.T.");
        this.arrayListNama.add("Edo Farrel Haryanto");
        this.arrayListNama.add("Dearen Hippy");
        this.arrayListNama.add("William Nehemia");
        this.arrayListNama.add("Alexander Bleuvito Fevrier");
    }
}
