package pl.edu.pb.ui.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.edu.pb.common.ui.composable.LoadingIndicator

@Composable
internal fun HomeLoadingContent(
    modifier: Modifier = Modifier,
) = LoadingIndicator(modifier = modifier)