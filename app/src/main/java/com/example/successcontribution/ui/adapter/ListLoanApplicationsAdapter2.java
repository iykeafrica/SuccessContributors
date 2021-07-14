package com.example.successcontribution.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.successcontribution.R;
import com.example.successcontribution.model.response.LoanRest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListLoanApplicationsAdapter2 extends ListAdapter<LoanRest, ListLoanApplicationsAdapter2.ViewHolder> {

    private ClickListener mClickListener;

    private static final DiffUtil.ItemCallback<LoanRest> diffCallback = new DiffUtil.ItemCallback<LoanRest>() {
        @Override
        public boolean areItemsTheSame(LoanRest oldItem, LoanRest newItem) {
            return oldItem.getLoanId().equals(newItem.getLoanId());
        }

        @Override
        public boolean areContentsTheSame(LoanRest oldItem, LoanRest newItem) {
            return oldItem.equals(newItem);
        }
    };

    public ListLoanApplicationsAdapter2() {
        super(diffCallback);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_loan_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListLoanApplicationsAdapter2.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView status;
        private final TextView amount;
        private final TextView statusDate;

        public ViewHolder(View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            amount = itemView.findViewById(R.id.amount);
            statusDate = itemView.findViewById(R.id.status_date);

            itemView.setOnClickListener(v -> {
                mClickListener.selectLoan(getAdapterPosition(), getItem(getAdapterPosition()));
            });

        }

        public void bind(LoanRest loanRest) {
            status.setText(loanRest.getStatus());
            amount.setText(loanRest.getAmount());

            DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
            statusDate.setText("" + dateFormat.format(new Date(loanRest.getRequestDate())));
        }
    }

    public interface ClickListener {
        void selectLoan(int position, LoanRest loanRest);
    }
}
