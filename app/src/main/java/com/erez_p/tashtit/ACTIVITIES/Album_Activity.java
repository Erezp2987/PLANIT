package com.erez_p.tashtit.ACTIVITIES;

import static com.erez_p.helper.BitMapHelper.decodeBase64;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.erez_p.model.TripPicture;
import com.erez_p.model.TripPictures;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.ADPTERS.BASE.GenericAdapter;
import com.erez_p.tashtit.ADPTERS.TripPictureAdapter;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.TripPictureViewModel;

public class Album_Activity extends BaseActivity {
    private ImageButton returnButton;
    private Button addButton;
    private RecyclerView recyclerView;
    private TripPictureAdapter adapter;
    private String tripId;
    private TripPictureViewModel viewModel;
    private TripPictures tripPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_album);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
        setViewModel();
        setRecyclerView();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        returnButton = findViewById(R.id.btnReturn);
        addButton = findViewById(R.id.btnAddPhoto);
        recyclerView = findViewById(R.id.rvPhotos);
        tripId = getIntent().getStringExtra("tripId");
    }
    public void setRecyclerView()
    {
        adapter = new TripPictureAdapter(tripPictures, R.layout.item_single_picture, holder -> {
            holder.putView("TripPicture", holder.itemView.findViewById(R.id.imageView));
            holder.putView("TripPictureDescription", holder.itemView.findViewById(R.id.textViewDescription));
        },((holder, item, position) -> {
            ((ImageView)holder.getView("TripPicture")).setImageBitmap(decodeBase64(item.getPictureUrl()));
            ((TextView)holder.getView("TripPictureDescription")).setText(item.getDescription());
        }));
        adapter.setOnItemLongClickListener(new GenericAdapter.OnItemLongClickListener<TripPicture>() {
            @Override
            public boolean onItemLongClick(TripPicture item, int position) {
                viewModel.delete(item);
                tripPictures.remove(item);
                adapter.notifyItemRemoved(position);
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void setListeners() {
        returnButton.setOnClickListener(v -> finish());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Album_Activity.this, Add_Photo_Activity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
            }
        });
        }

    @Override
    protected void setViewModel() {
        viewModel = new ViewModelProvider(this).get(TripPictureViewModel.class);
        viewModel.getTripPicturesByTripID(tripId);
        viewModel.getLiveDataCollection().observe(this, tripPictures -> {
            this.tripPictures = tripPictures;
            adapter.setItems(tripPictures);
        });
    }
}