package gxs.com.helpmepick;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener{

    ListView listView;
    EditText etInput;
    TextView tvShow;
    Button btAdd;
    Button btGo;
    Button btReX;
    Button btReS;
    List<String> list;
    LinearLayout llMain;
    RelativeLayout rlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        initView();
        rlText.setVisibility(View.GONE);
        //setListView();
    }

    private void setListView() {
        listView.setAdapter(new CommonAdapter<String>(this,R.layout.item_layout,list) {

            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_item,item);
                int yv = position%6;
                switch (yv){
                    case 0:
                        viewHolder.setBackgroundRes(R.id.tv_item,R.drawable.item_back_0);
                        break;
                    case 1:
                        viewHolder.setBackgroundRes(R.id.tv_item,R.drawable.item_back_1);
                        break;
                    case 2:
                        viewHolder.setBackgroundRes(R.id.tv_item,R.drawable.item_back_2);
                        break;
                    case 3:
                        viewHolder.setBackgroundRes(R.id.tv_item,R.drawable.item_back_3);
                        break;
                    case 4:
                        viewHolder.setBackgroundRes(R.id.tv_item,R.drawable.item_back_4);
                        break;
                    case 5:
                        viewHolder.setBackgroundRes(R.id.tv_item,R.drawable.item_back_5);
                        break;
                    default:
                        viewHolder.setBackgroundRes(R.id.tv_item,R.drawable.item_back_0);
                }
            }
        });
    }

    private void initView() {
        llMain = (LinearLayout)findViewById(R.id.ll_main);
        rlText = (RelativeLayout)findViewById(R.id.rl_text);
        listView = (ListView)findViewById(R.id.lv_listview);
        etInput = (EditText)findViewById(R.id.et_input);
        tvShow = (TextView)findViewById(R.id.tv_show);
        btAdd = (Button) findViewById(R.id.bt_add);
        btGo = (Button) findViewById(R.id.bt_go);
        btReX = (Button) findViewById(R.id.bt_re_x);
        btReS = (Button) findViewById(R.id.bt_re_s);
        etInput.addTextChangedListener(textWatcher);//输入框监听
        btAdd.setOnClickListener(this);
        btGo.setOnClickListener(this);
        btReX.setOnClickListener(this);
        btReS.setOnClickListener(this);
        listView.setOnItemLongClickListener(itemLong);//ListViewItem长按监听
    }
    int jilu = -1;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_add:
                String str = etInput.getText().toString().trim();

                if (TextUtils.isEmpty(str)){
                    Toast.makeText(this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    etInput.setText("");
                }else if (isContain(list,str)){
                    Toast.makeText(this,str+"已存在，不能重复添加",Toast.LENGTH_SHORT).show();
                    etInput.setText("");
                }else {
                    list.add(str);
                    setListView();
                    etInput.setText("");
                    setMainBake();
                }
                break;
            case R.id.bt_go:
                if (list.size() > 1){
                    llMain.setVisibility(View.GONE);
                    rlText.setVisibility(View.VISIBLE);
                    ValueAnimator animator = ValueAnimator.ofInt(0,24);
                    animator.setDuration(3000);
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            int currentValue = (int) valueAnimator.getAnimatedValue();
                            Log.e("TT",currentValue+"");
                            if (jilu != currentValue){
                                jilu = currentValue;
                                random();
                            }
                        }
                    });
                    animator.start();

                }else {
                    Toast.makeText(this,"至少需要两条数据",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.bt_re_x:
                llMain.setVisibility(View.VISIBLE);
                rlText.setVisibility(View.GONE);
                break;
            case R.id.bt_re_s:
                list.clear();
                setListView();
                llMain.setVisibility(View.VISIBLE);
                rlText.setVisibility(View.GONE);
                break;
        }
    }

    private void setMainBake() {
        int yv = list.size()%6;
        switch (yv){
            case 0:
                llMain.setBackgroundColor(getResources().getColor(R.color.item_back_1_a));
                break;
            case 1:
                llMain.setBackgroundColor(getResources().getColor(R.color.item_back_2_a));
                break;
            case 2:
                llMain.setBackgroundColor(getResources().getColor(R.color.item_back_4_a));
                break;
            case 3:
                llMain.setBackgroundColor(getResources().getColor(R.color.item_back_5_a));
                break;
            case 4:
                llMain.setBackgroundColor(getResources().getColor(R.color.item_back_3_a));
                break;
            case 5:
                llMain.setBackgroundColor(getResources().getColor(R.color.item_back_0_a));
                break;
            default:
                llMain.setBackgroundColor(getResources().getColor(R.color.item_back_1_a));
        }
    }

    private void random() {
        Random random = new Random();
        int i = random.nextInt(list.size());
        tvShow.setText(list.get(i));
        int s = random.nextInt(6);
        switch (s){
            case 0:
                tvShow.setTextColor(getResources().getColor(R.color.item_back_0_a));
                rlText.setBackgroundColor(getResources().getColor(R.color.item_back_1_a));
                break;
            case 1:
                tvShow.setTextColor(getResources().getColor(R.color.item_back_1_a));
                rlText.setBackgroundColor(getResources().getColor(R.color.item_back_2_a));
                break;
            case 2:
                tvShow.setTextColor(getResources().getColor(R.color.item_back_2_a));
                rlText.setBackgroundColor(getResources().getColor(R.color.item_back_4_a));
                break;
            case 3:
                tvShow.setTextColor(getResources().getColor(R.color.item_back_3_a));
                rlText.setBackgroundColor(getResources().getColor(R.color.item_back_5_a));
                break;
            case 4:
                tvShow.setTextColor(getResources().getColor(R.color.item_back_4_a));
                rlText.setBackgroundColor(getResources().getColor(R.color.item_back_3_a));
                break;
            case 5:
                tvShow.setTextColor(getResources().getColor(R.color.item_back_5_a));
                rlText.setBackgroundColor(getResources().getColor(R.color.item_back_0_a));
                break;
            default:
                tvShow.setTextColor(getResources().getColor(R.color.item_back_0_a));
                rlText.setBackgroundColor(getResources().getColor(R.color.item_back_1_a));
        }
    }

    private boolean isContain(List<String> list,String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(name)) {
                return true;
            }
        }
        return false;
    }

    TextWatcher textWatcher = new TextWatcher() {
        // 输入文本之前的状态
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // 输入文本中的状态
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        // 输入文本之后的状态
        @Override
        public void afterTextChanged(Editable s) {
            String str = etInput.getText().toString().trim();
            if ("520".equals(str)){
                Toast.makeText(MainActivity.this,"小萌萌，我喜欢你",Toast.LENGTH_LONG).show();
            }
        }
    };


    AdapterView.OnItemLongClickListener itemLong = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("确定删除?");
            builder.setTitle("提示");

            //添加AlertDialog.Builder对象的setPositiveButton()方法
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(list.remove(position)!=null){
                        System.out.println("success");
                    }else {
                        System.out.println("failed");
                    }
                    setListView();
                    Toast.makeText(getBaseContext(), "删除成功", Toast.LENGTH_SHORT).show();
                }
            });

            //添加AlertDialog.Builder对象的setNegativeButton()方法
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create().show();
            return false;
        }
    };



}
