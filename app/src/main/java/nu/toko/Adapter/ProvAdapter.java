package nu.toko.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Model.ProvModel;
import nu.toko.R;

public class ProvAdapter  extends ArrayAdapter<ProvModel> {

    Context context;
    int resource, textViewResourceId;
    List<ProvModel> items, tempItems, suggestions;

    public ProvAdapter(Context context, int resource, int textViewResourceId, List<ProvModel> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<ProvModel>(items); // this makes the difference.
        suggestions = new ArrayList<ProvModel>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.modelitem_autocomplete, parent, false);
        }
        ProvModel people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.tex);
            if (lblName != null)
                lblName.setText(people.getNama_provinsi());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((ProvModel) resultValue).getNama_provinsi();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ProvModel people : tempItems) {
                    if (people.getNama_provinsi().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<ProvModel> filterList = (ArrayList<ProvModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ProvModel people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}