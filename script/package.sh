# 打包全量aar脚本

echo "===start package aar==="
pwd

# 定义打包-正式包函数
function assembleRelease() {
    ./gradlew :${1}:assembleRelease
     mv "$PWD/${1}/build/outputs/aar/${1}-release.aar" "$PWD/$aar/${2}-${3}.aar" #  $PWD代表当前工作目录，等同于pwd
}

while read LINE
do
  # =~支持正则表达式，同时支持变量比较相等。==只能支持比较变量相不相等。
  # 可知：如果需要做两个变量的包含关系，可以使用=~匹配
  if  [[ $LINE =~ ^isEmBedLogkitSDK ]]; then # =～代表：正则匹配，左侧参数是否符合右侧参数要求；
      isEmBedLogkitSDK=${LINE#*:} #过滤LINE参数，删掉第一个：号及其左边的字符串
      isEmBedLogkitSDK=${isEmBedLogkitSDK//,/}
      echo "isEmBedLogkitSDK = $isEmBedLogkitSDK"
  elif [[ $LINE =~ ^loggerVersion ]]; then
      loggerVersion=${LINE#*:}
      loggerVersion=${loggerVersion//\"/}
      loggerVersion=${loggerVersion//,/}
      echo "loggerVersion = $loggerVersion"
  elif [[ $LINE =~ ^logcatVersion ]]; then
      logcatVersion=${LINE#*:}
      logcatVersion=${logcatVersion//\"/}
      logcatVersion=${logcatVersion//,/}
      echo "logcatVersion = $logcatVersion"
  elif [[ $LINE =~ ^logkitVersion ]]; then
      logkitVersion=${LINE#*:}
      logkitVersion=${logkitVersion//\"/}
      logkitVersion=${logkitVersion//,/}
      echo "logkitVersion = $logkitVersion"
  fi

done < "$PWD/script/config.gradle"


aar="repo_aar"
if [ ! -e ${aar} ];then  #如果文件不存在，-e 文件存在
  mkdir $aar
fi

./gradlew clean
if [ $isEmBedLogkitSDK == true ]; then
    assembleRelease "logkit" "logkit" $logkitVersion
else
    assembleRelease "logkit-logcat" "logcat" $logcatVersion
    assembleRelease "logkit-log" "log" $loggerVersion
fi

echo "===end package aar==="