package com.example.realestatemanager.ui.main

sealed class MainViewAction{
    object NavigateToPropertyDetailActivity : MainViewAction()
    object NavigateToCreatePropertyActivity : MainViewAction()
    object NavigateToPropertyMapExplorerActivity : MainViewAction()
    object NavigateToRealEstateLoanActivity : MainViewAction()
}
