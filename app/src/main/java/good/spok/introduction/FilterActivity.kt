package good.spok.introduction

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FilterActivity : AppCompatActivity() {

    companion object {
        val intent = Intent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter)

        val ab_view = layoutInflater.inflate(R.layout.action_bar_filter, null);
        ab_view.findViewById<ImageView>(R.id.action_bar_filter_close_imageView).setOnClickListener{
            onBackPressed()
        };
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM;
        supportActionBar?.customView = ab_view;
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        val arrayList = ArrayList<String>();
        arrayList.add("Все");
        arrayList.add("> 2-ух лет");
        arrayList.add("почти 2 года");
        arrayList.add("< 3-ёх месяцев");
        arrayList.add("< месяца");
        arrayList.add("< недели");

        val recyclerView = findViewById<RecyclerView>(R.id.filter_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter = DynamicAdapter(this, arrayList);
    }

    private class DynamicAdapter(var context : Activity,
                                   var arrayList: ArrayList<String>) : RecyclerView.Adapter<DynamicAdapter.ViewHolder>()
    {

        companion object {
            var checkBoxes = arrayOfNulls<CheckBox>(6);
        }

        open class ViewHolder(itemView: View, context: Activity) : RecyclerView.ViewHolder(itemView) {
            val checkBox : CheckBox = itemView.findViewById(R.id.card_view_filter_checkBox);
            init {
                checkBox.setOnClickListener {
                    if (adapterPosition == 0 && checkBox.isChecked) // All
                    {
                        intent.putExtra("filter", "nope")
                        context.setResult(1, intent)
                        for (i in 1 until checkBoxes.size)
                            checkBoxes[i]?.isChecked = true;
                    }
                    else if (checkBox.isChecked){
                        if (checkBoxes[0]?.isChecked == false) {
                            intent.putExtra("filter", checkBox.text.toString())
                            context.setResult(1, intent)
                        }
                    }
                };
            }
        }

        override fun getItemCount(): Int {
            return arrayList.size;
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view_filter, parent, false), context);
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.checkBox.setText(arrayList.get(position));
            checkBoxes[position] = holder.checkBox;
        }
    }

    override fun onBackPressed() {
        super.onBackPressed();
        finish();
    }
}