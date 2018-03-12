import codecs
import random


def random_char():
    char_code = random.randint(0x4e00, 0x9fbb)
    str = "\\u{}".format(format(char_code, 'x'))
    char = codecs.decode(str,'unicode_escape')
    return char


for x in range(10):
    print(random_char())