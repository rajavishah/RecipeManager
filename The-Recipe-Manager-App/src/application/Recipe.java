package application;

import java.io.ByteArrayInputStream;

import javafx.scene.image.Image;

public class Recipe {

	    private String name;
	    private String chefName;
	    private String ImgSrc;
		private double rating;


		private String description;
		private String ingredients;
		private String prepTime;
		private Image image;
		private byte[] imageData;

		private String category;
		private String comments;




	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getchefName() {
	        return chefName;
	    }

	    public void setchefName(String chefName) {
	        this.chefName = chefName;
	    }

	    public String getImage() {
	        return ImgSrc;
	    }

	    public void setImage(String ImgSrc) {
	    	System.out.println("----img----"+ImgSrc);
	        this.ImgSrc = ImgSrc;
	    }

	    public double getRating() {
	        return rating;
	    }

	    public void setRating(double rating) {
	        this.rating = rating;
	    }


	    public void setDescription(String description) {
	    	 this.description = description;
	    }
		public String getDescription() {

			return description;
		}

		public void setContents(String ingredients) {
			this.ingredients = ingredients;
		}

		public String getContents() {
			return ingredients;
		}

		public void setImageDetail(Image image) {
			this.image = image;
		}

		public Image getImageDetail(Image img) {
			return img;
		}


		public Image getImageFormat() {
			return image;
		}


		public String getPrepTime() {
			return prepTime;
		}

		public void setPrepTime(String prepTime) {
			this.prepTime = prepTime;
		}

		public void setByteImage(byte[] imageData) {

			    this.image = new Image(new ByteArrayInputStream(imageData));
			    this.imageData = imageData;

		}

		public byte[] getByteImage() {
			return imageData;
		}


		public void setCategory(String category) {
			this.category = category;
		}

		public String getCategory() {
			return category;
		}


		public String getComments() {

			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

	}

