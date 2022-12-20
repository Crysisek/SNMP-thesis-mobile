package pl.edu.pb.common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import pl.edu.pb.common.viewmodel.BaseViewModel

@Composable
inline fun <I> rememberIntent(
    viewModel: BaseViewModel<*, *, *, I>,
    crossinline createIntent: @DisallowComposableCalls () -> I,
) = remember(viewModel) {
    { viewModel.acceptIntent(createIntent()) }
}

@Composable
inline fun <T, I> rememberIntentWithParam(
    viewModel: BaseViewModel<*, *, *, I>,
    crossinline createIntent: @DisallowComposableCalls (T) -> I,
) = remember(viewModel) {
    { parameter: T -> viewModel.acceptIntent(createIntent(parameter)) }
}