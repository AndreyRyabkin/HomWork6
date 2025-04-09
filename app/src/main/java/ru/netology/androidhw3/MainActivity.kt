package ru.netology.androidhw3

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ru.netology.androidhw3.adapter.OnInteractionListener
import ru.netology.androidhw3.adapter.PostsAdapter
import ru.netology.androidhw3.databinding.ActivityMainBinding
import ru.netology.androidhw3.dto.Post

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likePost(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun repostPost(post: Post) {
                viewModel.repostPost(post.id)
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(this) // Устанавливаем LayoutManager
        binding.recyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.add.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isBlank()) {
                Toast.makeText(this, R.string.error_empty_content, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.changeContentandSave(text)
            binding.content.setText("")
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(it)

        }
          viewModel.edited.observe(this){
              if(it.id != 0L){
                  binding.content.setText(it.content)
                  binding.content.requestFocus()
                  binding.cancelEdit.visibility = View.VISIBLE
              } else {
                  binding.cancelEdit.visibility = View.GONE
              }
          }

        binding.cancelEdit.setOnClickListener {
            viewModel.cancelEdit()
            binding.content.setText("")
            binding.content.clearFocus()
        }




            ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
}
