package com.example.aston_intensiv_3.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.aston_intensiv_3.R
import com.example.aston_intensiv_3.databinding.ContactDialogBinding

const val ADD_CONTACT_RESULT_KEY = "ADD_CONTACT_RESULT_KEY"
const val BUNDLE_KEY_NAME = "BUNDLE_KEY_NAME"
const val BUNDLE_KEY_SURNAME = "BUNDLE_KEY_SURNAME"
const val BUNDLE_KEY_PHONE = "BUNDLE_KEY_PHONE"

class ChangeContactFragment : DialogFragment() {
    private var _binding: ContactDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ContactDialogBinding.inflate(LayoutInflater.from(context))

        val bundle = requireArguments()

        val name = bundle.getString(BUNDLE_KEY_NAME) ?: ""
        val surname = bundle.getString(BUNDLE_KEY_SURNAME) ?: ""
        val phoneNumber = bundle.getString(BUNDLE_KEY_PHONE) ?: ""

        binding.apply {
            etAddContactName.setText(name)
            etAddContactSurname.setText(surname)
            etAddContactPhoneNumber.setText(phoneNumber)
        }

        binding.btnAddContactSave.setOnClickListener {
            val resBundle = Bundle().apply {
                putString(BUNDLE_KEY_NAME, binding.etAddContactName.text.toString())
                putString(BUNDLE_KEY_SURNAME, binding.etAddContactSurname.text.toString())
                putString(BUNDLE_KEY_PHONE, binding.etAddContactPhoneNumber.text.toString())
            }
            parentFragmentManager.setFragmentResult(
                ADD_CONTACT_RESULT_KEY, resBundle
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
    }

    companion object {
        fun withArgs(bundle: Bundle = Bundle()): ChangeContactFragment {
            return ChangeContactFragment().apply {
                arguments = bundle
            }
        }
    }
}



