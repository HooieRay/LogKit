# Maven 发布全量包脚本
echo "start ====== publish to maven"

pwd
rm -rf component_logger/repo

./gradlew clean
./gradlew publish

echo "end ====== publish to maven"