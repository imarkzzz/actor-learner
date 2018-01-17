# -*- coding: utf-8 -*-
import random
import re
import codecs

# 没有注释的地方，自己看教程学完应该就懂，不用问我
# 文件命名
def fmt_file_name(ord):
  sto = str(ord)
  return "0"*(4-len(sto))+sto

# 英文句子判定
def contain_eng(line):
  pattern = "[a-zA-Z]"
  result = re.search(pattern, line)
  if result:
    return True
  return False

# 产生包含英文的文件序列号
def gen_english_file(lcount, english_lcount, split_unit):
  parts = lcount // split_unit if lcount % split_unit == 0 else lcount // split_unit + 1  
  arr = [i for i in range(parts)]
  random.shuffle(arr)
  head_n = english_lcount // 5 if english_lcount % 5 == 0 else english_lcount // 5 + 1
  return arr[:head_n]
  
def main():
  with codecs.open("语料.txt","r", "utf-8") as fin:
    line_pool = []
    english_line_pool = []
    split_unit = 410
    lcount = 0
    for line in fin:
      lcount += 1
      # 对是否包含英文的进行分类
      if contain_eng(line):
        english_line_pool.append(line)
        continue
      line_pool.append(line)
    random.shuffle(line_pool)
    english_lcount = len(english_line_pool) 
    candidate_english_files = gen_english_file(lcount, english_lcount, split_unit)
  
  lcount = 0
  # 将文件的数据分成410句的小文件
  out_dict = {}
  for line in line_pool:
    file_name = lcount//split_unit
    # 对候选包含英文的文件中添加5句包含英文的句子
    if file_name in candidate_english_files:
      fn = "out_dir/%s-English.txt" % fmt_file_name(file_name)
      if lcount % split_unit == 0:
        out_dict[fn] = []
        for i in range(5):
          if english_line_pool:
            out_dict[fn].append(english_line_pool.pop())
            lcount += 1
      out_dict[fn].append(line)
    else:
      fn = "out_dir/%s.txt" % fmt_file_name(file_name)
      if fn in out_dict:
        out_dict[fn].append(line)
      else:
        out_dict[fn] = [line]
    lcount += 1
  eng_file_count = 1
  no_eng_file_count = 1
  # 写入文件
  for k, v in out_dict.items():
    random.shuffle(v)
    # 分别对包含和不包含英文的文件进行重新命名(按出现次序命名)
    if "English" in k:
      eng_file_count += 1
      fn = "out_dir/%s-English.txt" % fmt_file_name(eng_file_count)
      with codecs.open(fn, "w", "utf-8") as fout:
        for line in v:
          fout.write(line)
    else:
      no_eng_file_count += 1
      fn = "out_dir/%s.txt" % fmt_file_name(no_eng_file_count)
      with codecs.open(fn, "w", "utf-8") as fout:
        for line in v:
          fout.write(line)
        
main()
  
  