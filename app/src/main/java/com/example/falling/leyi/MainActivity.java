package com.example.falling.leyi;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.falling.leyi.dbmanager.DBManager;
import com.example.falling.leyi.dbmanager.sqlglo;
import com.example.falling.leyi.ui.Everyday;
import com.example.falling.leyi.ui.Glossary;
import com.example.falling.leyi.ui.MainPage;
import com.example.falling.leyi.ui.Search;
import com.example.falling.leyi.ui.Thesaurus;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private MainPage mainPage = new MainPage();
    private Thesaurus thesaurus=null;
    private Glossary glossary = null ;
    private Everyday everyday = null ;
    private Toolbar toolbar;
    private MenuItem menuItem;
    private long mExitTime;
    FragmentManager fm = getFragmentManager();
    // 开启Fragment事务
    FragmentTransaction transaction = fm.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Search.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            new DBManager().createDatabase(this);
        } catch (IOException e) {
            e.printStackTrace();
        }


        transaction.add(R.id.main_fragment, mainPage);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuItem = menu.findItem(R.id.Add);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Add) {

            AlertDialog.Builder  alertbBuilder= new AlertDialog.Builder(this);
            final EditText NameEdt = new EditText(this);
            alertbBuilder.setTitle("请输入生词本名称:")
                    .setIcon(android.R.drawable.ic_menu_info_details)
                    .setView(NameEdt)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                             @Override
                            public void onClick(DialogInterface dialog, int which) {
                            //确定后要执行的语句
                                 String text = NameEdt.getText().toString();
                                 if(sqlglo.insertglo(text)){
                                     Toast.makeText(getApplicationContext(), "创建成功",
                                             Toast.LENGTH_SHORT).show();
                                 }else{
                                     Toast.makeText(getApplicationContext(), "创建失败，不能重名",
                                             Toast.LENGTH_SHORT).show();
                                 }
                                 glossary.updateView();
                            }
                        })
                    .setNegativeButton("取消",  new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .create();
            alertbBuilder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        // 开启Fragment事务
         transaction = fm.beginTransaction();

        hideFragments(transaction);

        int id = item.getItemId();

        if (id == R.id.nav_mainpage) {
            setTitle("LeYi");
            transaction.show(mainPage);

        } else if (id == R.id.nav_thesaurus) {

            setTitle("词库");
            if (thesaurus == null) {
                // 如果ContactsFragment为空，则创建一个并添加到界面上
                thesaurus = new Thesaurus();
                transaction.add(R.id.main_fragment, thesaurus);
            } else {
                // 如果ContactsFragment不为空，则直接将它显示出来
                transaction.show(thesaurus);
            }



        } else if (id == R.id.nav_glossary) {
            setTitle("生词本");
            menuItem.setVisible(true);
            if (glossary == null) {
                // 如果ContactsFragment为空，则创建一个并添加到界面上
                glossary = new Glossary();
                transaction.add(R.id.main_fragment, glossary);
            } else {
                // 如果ContactsFragment不为空，则直接将它显示出来
                transaction.show(glossary);
            }

        } else if (id == R.id.nav_remenbered) {
            setTitle("每日一句");
            if (everyday == null) {
                // 如果ContactsFragment为空，则创建一个并添加到界面上
                everyday = new Everyday();
                transaction.add(R.id.main_fragment, everyday);
            } else {
                // 如果ContactsFragment不为空，则直接将它显示出来
                transaction.show(everyday);
            }


        }
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void hideFragments(FragmentTransaction transaction) {

        menuItem.setVisible(false);
        if (mainPage != null) {
            transaction.hide(mainPage);
        }
        if (thesaurus != null) {
            transaction.hide(thesaurus);
        }
        if (glossary != null) {
            transaction.hide(glossary);
        }
        if (everyday != null) {
            transaction.hide(everyday);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
