package com.example.aston_intensiv_3.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.aston_intensiv_3.R
import com.example.aston_intensiv_3.databinding.AddContactDialogBinding

const val ADD_CONTACT_RESULT_KEY = "ADD_CONTACT_RESULT_KEY"
const val EDIT_CONTACT_RESULT_KEY = "EDIT_CONTACT_RESULT_KEY"
const val BUNDLE_KEY_NAME = "BUNDLE_KEY_NAME"
const val BUNDLE_KEY_SURNAME = "BUNDLE_KEY_SURNAME"
const val BUNDLE_KEY_PHONE = "BUNDLE_KEY_PHONE"

class AddContactFragment : DialogFragment() {
    private var _binding: AddContactDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = AddContactDialogBinding.inflate(LayoutInflater.from(context))
        parentFragmentManager.setFragmentResultListener(
            EDIT_CONTACT_RESULT_KEY, requireActivity()
        ) { _, bundle ->
            val name = bundle.getString(BUNDLE_KEY_NAME).toString()
            val surname = bundle.getString(BUNDLE_KEY_SURNAME).toString()
            val phoneNumber = bundle.getString(BUNDLE_KEY_PHONE).toString()
            Log.d("apptest", "get Bundle: name = $name surname = $surname")
            binding.apply {
                etAddContactName.setText(name)
                etAddContactSurname.setText(surname)
                etAddContactPhoneNumber.setText(phoneNumber)
            }
        }

        binding.btnAddContactSave.setOnClickListener {
            val bundle = Bundle().apply {
                putString(BUNDLE_KEY_NAME, binding.etAddContactName.text.toString())
                putString(BUNDLE_KEY_SURNAME, binding.etAddContactSurname.text.toString())
                putString(BUNDLE_KEY_PHONE, binding.etAddContactPhoneNumber.text.toString())
            }
            parentFragmentManager.setFragmentResult(
                ADD_CONTACT_RESULT_KEY, bundle
            )
            dismiss()
        }

        binding.btnAddContactCancel.setOnClickListener {
            dismiss()
        }

        return AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.add_contact_dialog_title))
            .setView(binding.root)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        parentFragmentManager.clearFragmentResultListener(EDIT_CONTACT_RESULT_KEY)
    }
}