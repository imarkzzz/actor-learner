1. grpc安装
2. protocol buffer学习
3. grpc 双向流
	在python中双向流使用yield实现
	双向流请求的频率不应快于请求处理时间，否则会发生部分请求得不到处理
4. sogou 的Auth请求
	先用双向流发送Auth请求，当返回200时，继续使用这个双线流发送语音请求。
	若发语音数据call Process则会新建一个双向流，导致没有权限进行语音识别处理
5. 语音识别的准确行判断可以使用 wer　算法(Levenshlein Distance)
