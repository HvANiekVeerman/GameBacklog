package com.example.gamebacklog.UI;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.gamebacklog.Model.Game;
import com.example.gamebacklog.Model.MainViewModel;
import com.example.gamebacklog.Model.RecyclerTouchListener;
import com.example.gamebacklog.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener,
        Serializable {

    FloatingActionButton fab;
    RecyclerViewAdapter mAdapter;
    RecyclerView mRecyclerView;
    View.OnClickListener mRestoreAllGames;
    View.OnClickListener mRestoreGame;
    private MainViewModel mMainViewModel;
    private List<Game> mGames;
    private Game mRestoredGame;
    private List<Game> mRestoredGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To change actionbar title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.mainActivityActionBar);

        // Handle clicks on floatingActionButton
        fab = findViewById(R.id.fabNewGame);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        // Links recyclerView to layout
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mGames = new ArrayList<>();
        mRestoredGames = new ArrayList<>();

        // Update the UI when the game list is changed
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.getGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> games) {
                mGames = games;
                updateUI();
            }
        });

        // Handles swipes on a card
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            // Handles when a card is dragged (impossible thus false)
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = (viewHolder.getAdapterPosition());

                Snackbar.make(findViewById(android.R.id.content), (mGames.get(position).
                        getMTitle()) + getString(R.string.gameDeleted), Snackbar.LENGTH_LONG).setAction(R.string.undo,
                        mRestoreGame).show();

                mRestoredGame = mGames.get(position);
                mMainViewModel.delete(mGames.get(position));
                mGames.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(this);

        mRestoreGame = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewModel.insert(mRestoredGame);
                updateUI();
            }
        };

        mRestoreAllGames = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mRestoredGames.size(); i++) {
                    mMainViewModel.insert(mRestoredGames.get(i));
                }
            }
        };

        // Handles when a card is clicked
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView,
                new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, EditGameActivity.class);
                Game gameToEdit = mGames.get(position);
                intent.putExtra("Game", gameToEdit);
                startActivityForResult(intent, 2);
            }
            // Handles longClicks (empty method because no longClicks)
            public void onLongClick(View view, int position) {}
        }));
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new RecyclerViewAdapter(mGames);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mGames);
        }
    }

    // This adds items to the action bar if it is present
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handle action bar item clicks here (delete all games)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_item) {
            Snackbar.make(findViewById(android.R.id.content), R.string.allGamesDeleted,
                    Snackbar.LENGTH_LONG).setAction(R.string.undo, mRestoreAllGames).show();
            mRestoredGames.addAll(mGames);
            for (int i = 0; i < mGames.size(); ) {
                mMainViewModel.delete(mGames.get(i));
                mGames.remove(i);
            }
            updateUI();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Called when you go back to mainActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String mDate = data.getStringExtra("date");
                String mTitle = data.getStringExtra("title");
                String mStatus = data.getStringExtra("status");
                String mPlatform = data.getStringExtra("platform");
                Game game = new Game(mTitle, mStatus, mPlatform, mDate);
                mMainViewModel.insert(game);
                updateUI();
            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                final Game editedGame = (Game) data.getSerializableExtra("Game");
                mMainViewModel.update(editedGame);
                updateUI();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) { return false; }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) { }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }

}
