package com.example.vetka.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vetka.R

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit = { _, _ -> },
    onRegister: () -> Unit = {}
) {
    var mail by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val pink = Color(0xFFFFABAE)
    val textColor = Color(0xFF252525)
    val secondaryColor = Color(0xFF858585)

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = textColor,
        unfocusedTextColor = textColor,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = pink,
        unfocusedBorderColor = Color(0xFFE5E5E5),
        focusedLabelColor = textColor,
        unfocusedLabelColor = secondaryColor,
        cursorColor = textColor
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clipToBounds()
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(48.dp))

            Text(
                text = "Vetka",
                color = textColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(56.dp))

            // Белая подложка формы
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 24.dp
                    )
                ) {
                    Text(
                        text = "Войдите в аккаунт",
                        color = textColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Чтобы делиться моментами с друзьями",
                        color = secondaryColor,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )

                    Spacer(Modifier.height(28.dp))

                    OutlinedTextField(
                        value = mail,
                        onValueChange = { mail = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Почта")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = fieldColors,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Пароль")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = fieldColors,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            TextButton(
                                onClick = {
                                    passwordVisible = !passwordVisible
                                }
                            ) {
                                Text(
                                    text = if (passwordVisible) {
                                        "Скрыть"
                                    } else {
                                        "Показать"
                                    },
                                    color = secondaryColor,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    )

                    Spacer(Modifier.height(28.dp))

                    Button(
                        onClick = {
                            onLogin(
                                mail.trim(),
                                password
                            )
                        },
                        enabled = mail.isNotBlank() &&
                                password.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 56.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = pink,
                            contentColor = textColor,
                            disabledContainerColor = pink.copy(
                                alpha = 0.4f
                            ),
                            disabledContentColor = textColor.copy(
                                alpha = 0.4f
                            )
                        )
                    ) {
                        Text(
                            text = "Войти",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    TextButton(
                        onClick = onRegister,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Создать аккаунт",
                            color = textColor,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(48.dp))
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LoginScreenPreview() {
    MaterialTheme(
        colorScheme = lightColorScheme()
    ) {
        LoginScreen()
    }
}


//clerk