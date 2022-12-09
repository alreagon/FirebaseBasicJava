//package com.zexceed.javaproject
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.View
//import android.webkit.MimeTypeMap
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.ProgressBar
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.storage.FirebaseStorage
//
//
//class MainActivityKotlin : AppCompatActivity() {
//    //widgets
//    private var uploadBtn: Button? = null
//    private var showAllBtn: Button? = null
//    private var imageView: ImageView? = null
//    private var progressBar: ProgressBar? = null
//
//    //vars
//    private val root = FirebaseDatabase.getInstance().getReference("Image")
//    private val reference = FirebaseStorage.getInstance().reference
//    private var imageUri: Uri? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        uploadBtn = findViewById(R.id.upload_btn)
//        showAllBtn = findViewById(R.id.showall_btn)
//        progressBar = findViewById(R.id.progressBar)
//        imageView = findViewById(R.id.imageView)
//        progressBar.setVisibility(View.INVISIBLE)
//        showAllBtn.setOnClickListener(View.OnClickListener {
//            startActivity(
//                Intent(
//                    this@MainActivity,
//                    ShowActivity::class.java
//                )
//            )
//        })
//        imageView.setOnClickListener(View.OnClickListener {
//            val galleryIntent = Intent()
//            galleryIntent.action = Intent.ACTION_GET_CONTENT
//            galleryIntent.type = "image/*"
//            startActivityForResult(galleryIntent, 2)
//        })
//        uploadBtn.setOnClickListener(View.OnClickListener {
//            if (imageUri != null) {
//                uploadToFirebase(imageUri!!)
//            } else {
//                Toast.makeText(this@MainActivity, "Please Select Image", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
//            imageUri = data.data
//            imageView!!.setImageURI(imageUri)
//        }
//    }
//
//    private fun uploadToFirebase(uri: Uri) {
//        val fileRef =
//            reference.child(System.currentTimeMillis().toString() + "." + getFileExtension(uri))
//        fileRef.putFile(uri).addOnSuccessListener {
//            fileRef.downloadUrl.addOnSuccessListener { uri ->
//                val model = Model(uri.toString())
//                val modelId = root.push().key
//                root.child(modelId!!).setValue(model)
//                progressBar!!.visibility = View.INVISIBLE
//                Toast.makeText(this@MainActivity, "Uploaded Successfully", Toast.LENGTH_SHORT)
//                    .show()
//                imageView!!.setImageResource(R.drawable.ic_add_photo_alternate_24)
//            }
//        }.addOnProgressListener { progressBar!!.visibility = View.VISIBLE }.addOnFailureListener {
//            progressBar!!.visibility = View.INVISIBLE
//            Toast.makeText(this@MainActivity, "Uploading Failed !!", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun getFileExtension(mUri: Uri): String? {
//        val cr = contentResolver
//        val mime = MimeTypeMap.getSingleton()
//        return mime.getExtensionFromMimeType(cr.getType(mUri))
//    }
//}