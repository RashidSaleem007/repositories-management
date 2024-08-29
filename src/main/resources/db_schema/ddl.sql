-- repos_db.t_owners definition
CREATE TABLE `t_owners` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `c_owner_email` varchar(255) NOT NULL,
  `c_owner_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- repos_db.t_repositories definition
CREATE TABLE `t_repositories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `c_clone_url` varchar(255) DEFAULT NULL,
  `c_repository_desc` varchar(255) DEFAULT NULL,
  `c_repository_name` varchar(255) DEFAULT NULL,
  `c_stars` int DEFAULT NULL,
  `c_owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf0xcn8hmm8j450tlpcy1vhola` (`c_owner_id`),
  CONSTRAINT `FKf0xcn8hmm8j450tlpcy1vhola` FOREIGN KEY (`c_owner_id`) REFERENCES `t_owners` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;