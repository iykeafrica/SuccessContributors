package com.example.successcontribution.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.successcontribution.R;
import com.example.successcontribution.model.response.UserRest;

import static com.example.successcontribution.shared.Constant.ADMIN;
import static com.example.successcontribution.shared.Constant.EXCO;
import static com.example.successcontribution.shared.Constant.LOAN_CHECKER;
import static com.example.successcontribution.shared.Constant.PRESIDENT;
import static com.example.successcontribution.shared.Constant.SUPER_ADMIN;

public class ListUsersAdapter extends ListAdapter<UserRest, ListUsersAdapter.ViewHolder> {

    private ClickListener mClickListener;

    private static final DiffUtil.ItemCallback<UserRest> diffCallback = new DiffUtil.ItemCallback<UserRest>() {
        @Override
        public boolean areItemsTheSame(UserRest oldItem, UserRest newItem) {
            return oldItem.getUserId().equals(newItem.getUserId());
        }

        @Override
        public boolean areContentsTheSame(UserRest oldItem, UserRest newItem) {
            return oldItem.equals(newItem);
        }
    };

    public ListUsersAdapter() {
        super(diffCallback);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListUsersAdapter.ViewHolder holder, int position) {
        if (getItem(position).getFirstName().equals(EXCO) || getItem(position).getFirstName().equals(LOAN_CHECKER)
                || getItem(position).getFirstName().equals(PRESIDENT) || (getItem(position).getFirstName().equals(ADMIN)
                || getItem(position).getFirstName().equals(SUPER_ADMIN))) {
            holder.cardHeader.setVisibility(View.GONE);
        } else {
            holder.cardHeader.setVisibility(View.VISIBLE);
            holder.bind(getItem(position));
            holder.itemView.setOnClickListener(v -> {
                mClickListener.selectUser(position, getItem(position));
            });
        }
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView department;
        CardView cardHeader;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            department = itemView.findViewById(R.id.department);
            cardHeader = itemView.findViewById(R.id.card_header);
        }

        public void bind(UserRest userRest) {
            String full_name = userRest.getFirstName() + " " + userRest.getLastName();
            name.setText(full_name);
            department.setText(userRest.getDepartment());
        }
    }

    public interface ClickListener {
        void selectUser(int position, UserRest userRest);
    }
}
