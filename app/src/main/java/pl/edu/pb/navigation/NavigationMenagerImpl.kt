package pl.edu.pb.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.edu.pb.common.di.ScopeMainImmediate
import pl.edu.pb.common.navigation.NavigationCommand
import pl.edu.pb.common.navigation.NavigationManager
import javax.inject.Inject

class NavigationManagerImpl @Inject constructor(
    @ScopeMainImmediate private val externalMainImmediateScope: CoroutineScope
) : NavigationManager {
    private val navigationCommandChannel = Channel<NavigationCommand>(Channel.BUFFERED)
    override val navigationEvent = navigationCommandChannel.receiveAsFlow()

    override fun navigate(command: NavigationCommand) {
        externalMainImmediateScope.launch {
            navigationCommandChannel.send(command)
        }
    }
}
