package com.example.app2_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ElementViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Element> mElementList;
    public ElementListAdapter(Context context) {
        mLayoutInflater=LayoutInflater.from(context);
        this.mElementList = new ArrayList<>();
        //rzutowanie kontekstu na OnItemClickListener i zapisanie
        //w polu mOnItemClickListener
        //należy zapewnić obsługę wyjątku ClassCastException
    }

    interface OnItemClickListener
    {
        void onItemClickListener(Element element);
    }
    private OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView= mLayoutInflater.inflate(R.layout.recyclerview_item,parent,false);
        ElementViewHolder ElementViewHolder=new ElementViewHolder(rootView);
        return ElementViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
        holder.bindToElementViewHolder(position);
    }
    @Override
    public int getItemCount() {
        if (mElementList!=null)
            return mElementList.size();
        return 0;
    }
    public class ElementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ProducentTextView;
        TextView ModelTextView;
        public ElementViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ProducentTextView=itemView.findViewById(R.id.ProducentTextView);
            ModelTextView = itemView.findViewById(R.id.ModelTextView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            //powiadomienie aktywności (mOnItemClickListener)
            //jaki element został wybrany
        }
        public void bindToElementViewHolder(int position)
        {
            ProducentTextView.setText("Producent: " + mElementList.get(position).getElement().getMProducent());
            ModelTextView.setText("Model: " + mElementList.get(position).getElement().getMModel());
        }
    }
    public void setElementList(List<Element> ElementList) {
        this.mElementList = ElementList;
        this.notifyDataSetChanged();
    }
}