package pl.edu.pb.ui.clientdetails.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.edu.pb.common.ui.composable.LoadingIndicator

@Composable
internal fun ClientDetailsLoadingContent(
    modifier: Modifier = Modifier,
) = LoadingIndicator(modifier = modifier)