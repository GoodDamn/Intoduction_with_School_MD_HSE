package good.spok.introduction

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayList : ArrayList<String> = ArrayList();
        val arrayListExp : ArrayList<String> = ArrayList();
        arrayList.add("header");
        arrayList.add("problem");
        arrayList.add("title");
        arrayList.add("C#"); arrayListExp.add("> 2-ух лет");
        arrayList.add("C++"); arrayListExp.add("> 2-ух лет");
        arrayList.add("Android-development (Java)"); arrayListExp.add("почти 2 года");
        arrayList.add("Swift"); arrayListExp.add("< 3-ёх месяцев");
        arrayList.add("Node.js"); arrayListExp.add("< месяца");
        arrayList.add("Kotlin"); arrayListExp.add("< недели");

        val recyclerView = findViewById<RecyclerView>(R.id.main_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter = DynamicAdapter(this, arrayList, arrayListExp);
    }

    protected class DynamicAdapter(var context : Activity,
                                   var arrayList: ArrayList<String>,
                                   var arrayListExp: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
    {

        class ViewHolderName(itemView: View, context: Activity) : RecyclerView.ViewHolder(itemView) {
            var b_github : Button = itemView.findViewById(R.id.button_github);

            init {
                b_github.setOnClickListener {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/GoodDamn")));
                };
            }
        }

        class ViewHolderSkills(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tv_language : TextView = itemView.findViewById(R.id.card_view_skills_tv_language);
            var tv_exp : TextView = itemView.findViewById(R.id.card_view_skills_tv_exp);
        }

        class ViewHolderProblem(itemView: View) : RecyclerView.ViewHolder(itemView){}
        class ViewHolderTitleSkills(itemView: View) : RecyclerView.ViewHolder(itemView){}

        override fun getItemViewType(position: Int): Int {
            return position;
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            when (viewType)
            {
                1 -> return ViewHolderProblem(LayoutInflater.from(parent.context).inflate(R.layout.card_view_problem, parent, false));
                2 -> return ViewHolderTitleSkills(LayoutInflater.from(parent.context).inflate(R.layout.card_view_title_skills, parent, false))
                in 3 until itemCount -> return ViewHolderSkills(LayoutInflater.from(parent.context).inflate(R.layout.card_view_skills, parent, false));
            }

            return  ViewHolderName(LayoutInflater.from(parent.context).inflate(R.layout.card_view_header, parent, false),
                context);
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder.itemViewType in 3 until itemCount)
            {
                var hold : ViewHolderSkills = holder as ViewHolderSkills;
                hold.tv_language.text = arrayList.get(position);
                hold.tv_exp.text = arrayListExp.get((itemCount- arrayListExp.size - position) * -1);

            }
        }

        override fun getItemCount(): Int {
            return arrayList.size;
        }


    }
}