import codecs
import random
import numpy as np
import string


def random_char():
    char_code = random.randint(0x4e00, 0x9fbb)
    str = "\\u{}".format(format(char_code, 'x'))
    char = codecs.decode(str,'unicode_escape')
    return char

# will print when import
for x in range(10):
    print(random_char())

def random_hex_unicode():
    char_code = random.randint(0x4e00, 0x9fbb)
    str = "\\u{}".format(format(char_code, 'x'))
    return str

def random_norm_code():
    choices = []
    choices += string.ascii_letters
    choices += string.digits
    choices += string.whitespace
    chosen_index = random.randint(0, len(choices) -1)
    return choices[chosen_index]


def random_mix_code(length, mix_rate=[0.5, 0.5]):
    mix_rate = np.array(mix_rate)
    mix_rate = mix_rate / mix_rate.sum()
    choices = {
        0: random_hex_unicode,
        1: random_norm_code
    }
    mix_choice = ''
    choices_keys = list(choices.keys())
    for i in range(length):
        chosen_index = np.random.choice(choices_keys, p=mix_rate)
        mix_choice += choices[chosen_index]()
    return mix_choice
    
def decode_mix_code_str(orinin_str):
    return codecs.decode(orinin_str,'unicode_escape')

def main():
    mix_code_str = random_mix_code(10)
    print(decode_mix_code_str(mix_code_str))

if __name__ == '__main__':
    main()