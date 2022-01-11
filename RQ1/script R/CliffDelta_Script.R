traditional <- read.csv(file.choose(), header = TRUE)
smartContract <- read.csv(file.choose(), header = TRUE)
for(i in 1:25){
res <- cliff.delta(traditional[,i], smartContract[,i])
cat(res$estimate,";",res$conf.int,";",res$var,";",res$conf.level,";",res$magnitude,";",res$variance.estimation,";",res$CI.distribution,";\n")
}

