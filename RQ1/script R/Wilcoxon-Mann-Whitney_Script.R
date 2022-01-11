traditional <- read.csv(file.choose(), header = TRUE)
smartContract <- read.csv(file.choose(), header = TRUE)
for(i in 1:25){
res <- wilcox.test(traditional[,i], smartContract[,i])
print(res$p.value)
}