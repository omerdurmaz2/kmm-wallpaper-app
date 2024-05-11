package screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import org.koin.compose.getKoin
import components.PiProgressIndicator
import components.ProductCard

@Composable
fun HomeScreen(navigateToDetail: () -> Unit) {
    val viewModel: HomeViewModel = getKoin().get()
    val homeScreenState by viewModel.homeViewState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getImages()
    }
    when (homeScreenState) {
        is HomeViewModel.HomeScreenState.Loading -> {
            PiProgressIndicator()
        }

        is HomeViewModel.HomeScreenState.Success -> {
            val products =
                (homeScreenState as HomeViewModel.HomeScreenState.Success).responseData.photos
            ProductCard(products.orEmpty(), onSelect = navigateToDetail)
        }

        is HomeViewModel.HomeScreenState.Error -> {
            val message =
                (homeScreenState as HomeViewModel.HomeScreenState.Error).errorMessage
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(message)
            }
            //show Error
        }
    }
}

