package com.rayadams.hiltexample.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rayadams.hiltexample.R
import com.rayadams.hiltexample.views.view_models.EditContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactView(viewModel: EditContactViewModel= hiltViewModel()) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(stringResource(R.string.txt_edit_contact)) },
            navigationIcon = {
                IconButton(onClick = { viewModel.goBack() }) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.txt_back)
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { viewModel.save() },
                    enabled = viewModel.canBeSaved
                ) {
                    Icon(Icons.Default.Check, contentDescription = stringResource(R.string.txt_add_contact))
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            TextField(value = viewModel.firstName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                label = { Text(stringResource(R.string.first_name)) },
                onValueChange = {
                    viewModel.updateFirstName(it)
                })
            TextField(value = viewModel.lastName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                label = { Text(stringResource(R.string.last_name)) },
                onValueChange = {
                    viewModel.updateLastName(it)
                })
            TextField(value = viewModel.phoneNumber,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.phone_number)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                onValueChange = {
                    viewModel.updatePhoneNumber(it)
                })
        }
    }
}