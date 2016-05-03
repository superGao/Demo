package supergao.www.mycardview;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import supergao.www.mycardview.view.CardAdapter;
import supergao.www.mycardview.view.CardView;

/**
 * cardView
 */
public class MainActivity extends AppCompatActivity implements CardView.OnCardClickListener {
    List<String> list;
    private CardFragment cardFragment;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        cardView = (CardView) findViewById(R.id.cardView1);
        cardView.setOnCardClickListener(this);
        cardView.setItemSpace(Utils.convertDpToPixelInt(this, 20));

        MyCardAdapter adapter = new MyCardAdapter(this);
        adapter.addAll(initData());
        cardView.setAdapter(adapter);

        FragmentManager manager = getSupportFragmentManager();
        cardFragment = new CardFragment();
        manager.beginTransaction().add(R.id.contentView, cardFragment).commit();
    }

    private List<String> initData() {
        list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("six");
        list.add("seven");
        return list;
    }

    @Override
    public void onCardClick(View view, int position) {
        Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("text", list.get(position % list.size()));
        cardFragment.show(view, bundle);
    }

    public class MyCardAdapter extends CardAdapter<String> {

        public MyCardAdapter(Context context) {
            super(context);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected View getCardView(int position,
                                   View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                convertView = inflater.inflate(R.layout.item_layout, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.textView1);
            String text = getItem(position % list.size());
            tv.setText(text);
            return convertView;
        }
    }
}
