package com.asadbek.rxkotlin


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.asadbek.rxkotlin.databinding.ActivityMainBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

// AsnycTask bilan deyarli bir xil ish qiladi...
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private  val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observab - obektlarni eshitib turadigan xususiyat
        /**
         *
         *
         *
         */

        // observable funktsiyasini ishlatish
       // val observable = createButtonClickObservable()
       /*
        observable.subscribe{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onCreate: $it")

        }
        */


        // EditText bilan misol
        val observable = createEditTextChangeObservable()
        observable.subscribeOn(Schedulers.io())
           // .observeOn(AndroidSchedulers.mainThread()) // ishni mainthreadda bajarishi...
    //        .map {  // keladigan ma`lumotni o`zgartirib beradi
     //           it.plus(it) // keladigan ma`lumotga nimadr qo`shish
     //       }
      //      .filter {
      //          it.length == 6 // shart berish - bu misolda kelayotgan malumot o`lchami 6 ga teng bo`lganda shu ish bajariladi
      //      }

            .debounce(5L,TimeUnit.SECONDS) // ishni 5 sekundan keyin bajaradi. vaqtinchaklik to`xtatib turadi.
            //  debounce - mainthreadda - view lar uchun ishlamaydi xatolik tashlaydi,subscibeda yoziladiganlar faqat backend uchun view lar uchun handler ishlatish kerak
            .subscribe {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }

    }

    // observable funksiya
    private fun createButtonClickObservable():Observable<String>{ // onNext() - string oladi agar funning qaytaradigan qiymati string bo`lsa unda boshqa tip
        return Observable.create{emitter -> // emitter - rx java ichida bor xabarlarni yuborib turadigan classi
            // button bosilganda ishlaydi boshqa bir harakatlarxam qo`shish mumkin
            binding.btn.setOnClickListener {
                emitter.onNext("Azimov") // button bosilishini tinglaydi ha berilgan xiymatni chiqarib beradi yoki yuboradi

            }
            emitter.setCancellable(null) // emitter  ish yakunlangandan keyin uni bekor qiladi ya`ni to`xtatadi.
        }


    }

    private fun createEditTextChangeObservable():Observable<String>{
        return Observable.create{ emitter ->
            binding.edt1.addTextChangedListener {
                emitter.onNext(it.toString())
            }
        }
    }
}