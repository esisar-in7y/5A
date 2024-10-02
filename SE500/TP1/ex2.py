# python3 -m pip install pycryptodomex
import base64
import hashlib
import os
from Cryptodome.Cipher import AES

HASH_NAME = "SHA256"
IV_LENGTH = 12
ITERATION_COUNT = 10
KEY_LENGTH = 32
SALT_LENGTH = 16
TAG_LENGTH = 16

def encrypt(password, plain_message):
    salt = os.urandom(SALT_LENGTH)
    iv = os.urandom(IV_LENGTH)

    secret = hashlib.pbkdf2_hmac(
        HASH_NAME, password.encode(), salt, ITERATION_COUNT, KEY_LENGTH
    )

    cipher = AES.new(secret, AES.MODE_GCM, iv)

    encrypted_message, tag = cipher.encrypt_and_digest(plain_message.encode("utf-8"))
    print("TAG:",tag)

    return base64.b64encode(salt + iv + encrypted_message+ tag).decode()


def decrypt(password, cipher_message):
    decoded_cipher = base64.b64decode(cipher_message)

    salt = decoded_cipher[:SALT_LENGTH]
    iv = decoded_cipher[SALT_LENGTH : SALT_LENGTH + IV_LENGTH]
    encrypted_message = decoded_cipher[SALT_LENGTH + IV_LENGTH : -TAG_LENGTH]
    tag = decoded_cipher[-TAG_LENGTH:]

    secret = hashlib.pbkdf2_hmac(
        HASH_NAME, password.encode(), salt, ITERATION_COUNT, KEY_LENGTH
    )

    cipher = AES.new(secret, AES.MODE_GCM, iv)

    decrypted_message = cipher.decrypt_and_verify(encrypted_message, tag)

    return decrypted_message.decode("utf-8")

secret_key = "I swear I killed everyone"
plain_text = "I swear I didn't kill him"

print("AES-GCM Encryption")
cipher_text = encrypt(secret_key, plain_text)
print("encryption input:", plain_text)
print("encryption output:", cipher_text)


decrypted_text = decrypt(secret_key, cipher_text)

print("AES-GCM Decryption")
print("decryption input:", cipher_text)
print("decryption output:", decrypted_text)


print("Same message Encrypted")
print("encryption output:", encrypt(secret_key, plain_text))
print("encryption output:", encrypt(secret_key, plain_text))
print("encryption output:", encrypt(secret_key, plain_text))
