package com.tdmr.demo.two.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tdmr.demo.two.DbUtils;
import com.tdmr.demo.two.databinding.ItemBadgeBinding;
import com.tdmr.demo.two.models.Contact;

import java.util.List;


/**
 * Created by abigail on 5/26/2016.
 */
public class PicsAdapter extends RecyclerView.Adapter<PicsAdapter.ViewHolder> {
    private List<Contact> selectedIds;

    public PicsAdapter(List<Contact> selectedIds) {
        this.selectedIds = selectedIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBadgeBinding binding = ItemBadgeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder vh = new ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Uri sku = DbUtils.getPhotoUri(selectedIds.get(position).getId());
        holder.binding.setPhotoUri(sku);
    }

    @Override
    public int getItemCount() {
        return selectedIds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemBadgeBinding binding;

        public ViewHolder(ItemBadgeBinding v) {
            super(v.getRoot());
            binding = v;
        }
    }
}