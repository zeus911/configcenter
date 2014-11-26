#!/bin/sh

tar_execute(){

    module_name=$1   #输入的模块名
    extension=$2     #扩展名，war或者zip
    file_name=${module_name}"."${extension}
    archer_conf="./archer/"${module_name}"/conf"
    archer_noahdes="./archer/"${module_name}"/noahdes"
    archer_bin="./archer/bin"

    tar_name=${module_name}".tar.gz"
    current_time=$(date +%y%m%d%H%M%S)

    temp_dir="./output/temp_"${current_time}
    mkdir ${temp_dir}
    cp "./output/"${file_name} ${temp_dir}
    cp -r ${archer_conf} ${temp_dir}
    cp -r ${archer_noahdes} ${temp_dir}
    
    cd ${temp_dir}

	unzip -o ${file_name} && rm ${file_name}

	if test $extension = "zip" 
	then
		cp -r ./${module_name}/* ./
		rm -r ${module_name}
	fi
		
    tar -zcf ${tar_name} *
    cd ../..
    cp ${temp_dir}/${tar_name} "./output" && rm -rf ${temp_dir}

    echo ${module_name}".tar.gz has been already prepeared!"
    return

}
