package com.example.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {
   private val _categoriesState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoriesState

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories()
                _categoriesState.value =_categoriesState.value.copy(
                    categories = response.categories,
                    loading = false,
                    error = null
                )

            } catch (e:Exception){
                _categoriesState.value =_categoriesState.value.copy(
                    loading = false,
                    error = ("Error Fetching Categories ${e.message}")
                )
            }
        }
    }

    data class RecipeState(
        val loading:Boolean = true,
        val categories: List<Category> = emptyList(),
        val error: String? = null
    )


}
