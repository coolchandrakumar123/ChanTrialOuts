package com.chan.chandummy

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.chan.chandummy.bottomsheet.BottomMenuFragment
import com.chan.chandummy.customview.CustomBottomSheetDialog
import com.chan.chandummy.util.getBlurCircle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    var parentThread: Thread? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(resources.getIdentifier("AppTheme.MainPage", "style", packageName))
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        first.setOnClickListener {
            switchView()
        }

        second.setOnClickListener {
            switchView()
        }

        fab.setOnClickListener { view ->
            //openTicketInRadar(648638721, 196608000014373120)
            //thirdName.text = "33333 This is extra statement to identify the ellipsize"
            //startActivity(Intent(this, RecyclerViewActivity::class.java))
            showBottomMenu()
        }

        button1.setOnClickListener {
            firstName.text = "111111This is Contact Name, this is extra statement to identify the ellipsize"
            checkLambdaForFunctionPassing()
        }

        button2.setOnClickListener {
            //second.text = getString(R.string.dummy_text)
            lastName.text = "2222222This is Account Name, this is extra statement to identify the ellipsize"
        }
        textImageView.setText("AB")
        setValuesInitially()
        progressBar.percentage = 65f
        gradientImageView.background = getBlurCircle(this, intArrayOf(R.color.static_red, R.color.static_white))
    }

    private fun showDialog() {
        val dialog = CustomBottomSheetDialog()
        dialog.isCancelable = false
        dialog.show(supportFragmentManager)
    }

    private fun showBottomMenu() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_wrapper, BottomMenuFragment())
            commit()
        }
    }

    private fun stopThread() {
        parentThread?.suspend()
    }

    private fun startThreadWithChild() {
        parentThread = Thread(Runnable {
            Thread(Runnable {
                while (true) {
                    Thread.sleep(1000)
                    Log.d("ChanLog", "Inside: 1");
                }
                /*Thread(Runnable {
                    while (true) {
                        Log.d("ChanLog", "Inside: 2");
                    }
                }).start()*/
            }).start()
            while (true) {
                Thread.sleep(1000)
                Log.d("ChanLog", "Parent Thread");
            }
        }
        )
        parentThread?.start()
    }

    private fun openTicketInRadar(orgId: Long, ticketId: Long) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("zohodeskradar://?orgId=$orgId&ticketId=$ticketId"))
        val activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        val isIntentSafe = activities.size > 0
        if (isIntentSafe) {
            startActivity(intent)
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setPositiveButton("Ok", null)
            builder.setView(TextView(this).apply {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                val string = SpannableString("Download Radar from PlayStore\n\nhttps://play.google.com/store/apps/details?id=com.zoho.desk.radar")  // No I18N
                Linkify.addLinks(string, Linkify.WEB_URLS)
                setPadding(16, 16, 16, 16)
                text = string
                movementMethod = LinkMovementMethod.getInstance()
            })
            builder.setCancelable(true)
            builder.show()
        }
    }

    private fun setValuesInitially() {
        firstName.text = "111111This is Contact Name, this is extra statement to identify the ellipsize"
        lastName.text = "2222222This is Account Name, this is extra statement to identify the ellipsize"
        thirdName.text = "33333 This is extra statement to identify the ellipsize"
    }

    private fun switchView() {
        if(second.visibility == View.GONE) {
            second.text = getString(R.string.dummy_text)
            first.visibility = View.INVISIBLE
            second.visibility = View.VISIBLE
        } else {
            first.visibility = View.VISIBLE
            second.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkLambdaForFunctionPassing() {

        lambdaForNoParameters {
            Log.d("ChanLog", "lambdaForNoParameters")
        }

        lambdaForParameters { value1, value2 ->
            Log.d("ChanLog", "insidetempLambdaCheck: value1 = $value1, value2 = $value2")
            "test: ${value1+value2}"
        }

        lambdaForCallback ({ callback ->
            callback.onSuccess()
            10
        }, "Test")

        "ChandraEmptyCheck".emptyCheck {
            Log.d("ChanLog", "NonEmptyValue: $it");
        }?.let {
            Log.d("ChanLog", "afterNonEmptyValue: $it");
        }

        "Mohan".emptyCheckFunction {
            
        }?.run {

        }
    }

    private fun String?.emptyCheck(ifTrue: (String) -> Unit): String? {
        if(this != null && this.isNotEmpty()) {
            ifTrue(this)
        }
        return this
    }

    private fun String?.emptyCheckFunction(ifTrue: String.() -> Unit): String? {
        if(this != null && this.isNotEmpty()) {
            ifTrue()
        }
        return this
    }


    private fun lambdaForNoParameters(test : () -> Unit) {
        test()
    }

    private fun lambdaForParameters(test : (Int, Int) -> String) {
        Log.d("ChanLog", "value: ${test(5,10)}")
    }

    private fun lambdaForCallback(callBack: (ClassCallBack) -> Int, checkPassingValue: String) {
        callBack(object : ClassCallBack {
            override fun onSuccess() {
                Log.d("ChanLog", "checkPassingValue: $checkPassingValue");
            }

            override fun onFailure() {

            }
        })
    }

    private interface ClassCallBack {
        fun onSuccess()
        fun onFailure()
    }
}
